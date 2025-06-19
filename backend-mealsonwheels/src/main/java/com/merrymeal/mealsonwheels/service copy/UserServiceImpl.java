package com.merrymeal.mealsonwheels_backend.service;

import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import com.merrymeal.mealsonwheels_backend.model.Role;
import com.merrymeal.mealsonwheels_backend.model.User;
import com.merrymeal.mealsonwheels_backend.repository.RoleRepository;
import com.merrymeal.mealsonwheels_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public UserDTO approveUser(Long userId, boolean approved) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setApproved(approved);
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());

        // Update role if roleName is present in DTO
        if (userDTO.getRoleName() != null && !userDTO.getRoleName().isEmpty()) {
            Role role = roleRepository.findByName(userDTO.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        // No userType handling here anymore

        return mapToDTO(userRepository.save(user));
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setApproved(user.isApproved());
        
        // Removed dto.setUserType(...) because it's gone

        if (user.getRole() != null) {
            dto.setRoleName(user.getRole().getName());
        }
        return dto;
    }
}
