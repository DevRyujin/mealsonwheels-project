package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels_backend.dto.TaskDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.VolunteerDTO;
import com.merrymeal.mealsonwheels_backend.model.Volunteer;
import com.merrymeal.mealsonwheels_backend.repository.VolunteerRepository;
import com.merrymeal.mealsonwheels_backend.security.SecurityUtil;
import com.merrymeal.mealsonwheels_backend.service.adminService.TaskService;
import com.merrymeal.mealsonwheels_backend.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final TaskService taskService;

    @Override
    public VolunteerDTO getVolunteerProfile() {
        Long userId = SecurityUtil.getCurrentUserId();
        Volunteer volunteer = volunteerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found with ID: " + userId));

        // Add validations
        UserValidationUtil.checkApproved(volunteer);
        UserValidationUtil.checkRole(volunteer, "VOLUNTEER");

        return VolunteerDTO.builder()
                .id(volunteer.getId())
                .username(volunteer.getUsername())
                .email(volunteer.getEmail())
                .phoneNumber(volunteer.getPhoneNumber())
                .availability(volunteer.getAvailability())
                .services(volunteer.getServices())
                .build();
    }

    @Override
    public List<TaskDTO> getAssignedTasks() {
        Long userId = SecurityUtil.getCurrentUserId();
        return taskService.getTasksByVolunteerId(userId);
    }
}
