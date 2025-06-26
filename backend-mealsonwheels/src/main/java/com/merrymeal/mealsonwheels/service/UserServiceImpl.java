package com.merrymeal.mealsonwheels.service;

import com.merrymeal.mealsonwheels.dto.UserDTO;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        user.setName(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhoneNumber());

        return mapToDTO(userRepository.save(user));
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getName());
        dto.setPhoneNumber(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setApproved(user.isApproved());
        dto.setRoleName(user.getRole().name());
        return dto;
    }
}
