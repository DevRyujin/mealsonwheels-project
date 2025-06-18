package com.merrymeal.mealsonwheels_backend.service;

import com.merrymeal.mealsonwheels_backend.dto.LoginDTO;
import com.merrymeal.mealsonwheels_backend.dto.RegisterDTO;
import com.merrymeal.mealsonwheels_backend.dto.UserDTO;
import com.merrymeal.mealsonwheels_backend.exception.AccountNotApprovedException;
import com.merrymeal.mealsonwheels_backend.model.*;
import com.merrymeal.mealsonwheels_backend.repository.RoleRepository;
import com.merrymeal.mealsonwheels_backend.repository.UserRepository;
import com.merrymeal.mealsonwheels_backend.security.CustomUserDetails;
import com.merrymeal.mealsonwheels_backend.security.JwtTokenProvider;
import com.merrymeal.mealsonwheels_backend.service.mealOrderService.LocationIQService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
// Import AuthenticationConfiguration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct; // Import @PostConstruct for initialization
import java.io.IOException;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    // REMOVE direct @Autowired on AuthenticationManager
    // @Autowired
    // private AuthenticationManager authenticationManager;

    // Autowire AuthenticationConfiguration instead
    private final AuthenticationConfiguration authenticationConfiguration;
    private AuthenticationManager authenticationManager; // This will hold the manager instance

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private LocationIQService locationIQService;

    // Use constructor injection for AuthenticationConfiguration
    public AuthServiceImpl(AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationConfiguration = authenticationConfiguration;
    }

    // Use @PostConstruct to get the AuthenticationManager after dependencies are
    // injected
    @PostConstruct
    public void init() throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public String login(LoginDTO loginDTO) {
        try {
            // Authenticate using email and password
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()));
            logger.info("User login successful for email: {}", loginDTO.getEmail());
        } catch (BadCredentialsException e) {
            logger.warn("Failed login attempt for email: {}", loginDTO.getEmail());
            throw new RuntimeException("Invalid email or password");
        } catch (DisabledException e) {
            logger.warn("Login failed: account not approved for user: {}", loginDTO.getEmail());
            // Changed to throw AccountNotApprovedException for proper handling
            throw new AccountNotApprovedException("Account is pending admin approval.");
        }

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> {
                    logger.error("Login failed: user not found for email: {}", loginDTO.getEmail());
                    return new RuntimeException("User not found");
                });

        // Additional check — optional (Spring already uses isEnabled internally)
        if (!user.isApproved()) {
            logger.warn("Login failed: account not approved for user: {}", loginDTO.getEmail());
            throw new AccountNotApprovedException("Account is pending admin approval.");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        return jwtTokenProvider.generateToken(userDetails, user.getId());
    }

    @Override
    @Transactional
    public UserDTO register(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            logger.warn("Registration failed: username already taken - {}", registerDTO.getUsername());
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            logger.warn("Registration failed: email already registered - {}", registerDTO.getEmail());
            throw new RuntimeException("Email already registered");
        }

        Role role = roleRepository.findByName(registerDTO.getRoleName())
                .orElseThrow(() -> {
                    logger.error("Registration failed: role not found - {}", registerDTO.getRoleName());
                    return new RuntimeException("Role not found: " + registerDTO.getRoleName());
                });

        String encodedPassword = passwordEncoder.encode(registerDTO.getPassword());

        User user = createUserInstance(registerDTO, role, encodedPassword);

        // approved true only if admin role
        user.setApproved(role.getName().equalsIgnoreCase("ROLE_ADMIN"));

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: username={}, email={}, role={}", user.getUsername(), user.getEmail(),
                role.getName());

        return mapToDTO(savedUser);
    }

    private User createUserInstance(RegisterDTO dto, Role role, String encodedPassword) {
        String roleName = role.getName().toUpperCase();

        switch (roleName) {
            case "ROLE_ADMIN":
                return new Admin(dto.getUsername(), dto.getPhoneNumber(), dto.getEmail(), encodedPassword, true, role);

            case "ROLE_MEMBER":
                double lat, lon;
                try {
                    double[] coords = locationIQService.getCoordinatesFromAddress(dto.getAddress());
                    lat = coords[0];
                    lon = coords[1];
                    logger.info("Fetched coordinates for Member address: {} => lat: {}, lon: {}", dto.getAddress(), lat,
                            lon);
                } catch (IOException e) {
                    logger.error("Failed to fetch coordinates for member address: {}", dto.getAddress(), e);
                    throw new RuntimeException("Failed to fetch coordinates for member address: " + e.getMessage());
                }

                return new Member(
                        dto.getUsername(),
                        dto.getPhoneNumber(),
                        dto.getEmail(),
                        encodedPassword,
                        false,
                        role,
                        dto.getDietaryRestriction(),
                        dto.getAddress(),
                        lat,
                        lon);

            case "ROLE_VOLUNTEER":
                return new Volunteer(dto.getUsername(), dto.getPhoneNumber(), dto.getEmail(), encodedPassword, false,
                        role,
                        dto.getAvailability(), dto.getServices());

            case "ROLE_CAREGIVER":
                return new Caregiver(dto.getUsername(), dto.getPhoneNumber(), dto.getEmail(), encodedPassword, false,
                        role,
                        dto.getQualificationAndSkills());

            case "ROLE_RIDER":
                return new Rider(dto.getUsername(), dto.getPhoneNumber(), dto.getEmail(), encodedPassword, false, role,
                        dto.getDriverLicense());

            case "ROLE_PARTNER":
                double partnerLat;
                double partnerLong;
                try {
                    double[] coords = locationIQService.getCoordinatesFromAddress(dto.getCompanyAddress());
                    partnerLat = coords[0];
                    partnerLong = coords[1];
                    logger.info("Fetched coordinates for Partner company address: {} => lat: {}, lon: {}",
                            dto.getCompanyAddress(), partnerLat, partnerLong);
                } catch (IOException e) {
                    logger.error("Failed to fetch coordinates for company address: {}", dto.getCompanyAddress(), e);
                    throw new RuntimeException("Failed to fetch coordinates for company address: " + e.getMessage());
                }

                return new Partner(
                        dto.getUsername(),
                        dto.getPhoneNumber(),
                        dto.getEmail(),
                        encodedPassword,
                        false,
                        role,
                        dto.getCompanyName(),
                        dto.getCompanyDescription(),
                        dto.getCompanyAddress(),
                        partnerLat,
                        partnerLong);

            case "ROLE_SUPPORTER":
                return new Supporter(dto.getUsername(), dto.getPhoneNumber(), dto.getEmail(), encodedPassword, false,
                        role,
                        dto.getSupportType(), dto.getSupdescription());

            case "ROLE_DONOR":
                return new Donor(dto.getUsername(), dto.getPhoneNumber(), dto.getEmail(), encodedPassword, true, role,
                        dto.getDonorType(), dto.getDonationAmount());

            default:
                logger.error("Unsupported role for registration: {}", roleName);
                throw new RuntimeException("Unsupported role for registration: " + roleName);
        }
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setApproved(user.isApproved());
        dto.setRoleName(user.getRole() != null ? user.getRole().getName() : null);
        return dto;
    }
}