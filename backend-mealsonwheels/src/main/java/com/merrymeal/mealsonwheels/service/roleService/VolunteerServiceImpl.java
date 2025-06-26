package com.merrymeal.mealsonwheels.service.roleService.impl;

import com.merrymeal.mealsonwheels.dto.TaskDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.VolunteerProfileDTO;
import com.merrymeal.mealsonwheels.model.Task;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.model.VolunteerProfile;
import com.merrymeal.mealsonwheels.repository.TaskRepository;
import com.merrymeal.mealsonwheels.repository.VolunteerProfileRepository;
import com.merrymeal.mealsonwheels.service.roleService.VolunteerService;
import com.merrymeal.mealsonwheels.util.SecurityUtil;
import com.merrymeal.mealsonwheels.util.UserValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerProfileRepository volunteerProfileRepository;
    private final TaskRepository taskRepository;

    @Override
    public VolunteerProfileDTO getVolunteerProfile() {
        VolunteerProfile volunteer = getCurrentVolunteer();
        return mapToDTO(volunteer);
    }

    @Override
    public List<TaskDTO> getAssignedTasks() {
        VolunteerProfile volunteer = getCurrentVolunteer();
        List<Task> tasks = taskRepository.findByVolunteer(volunteer);
        return tasks.stream()
                .map(this::mapToTaskDTO)
                .collect(Collectors.toList());
    }

    private VolunteerProfile getCurrentVolunteer() {
        Long userId = SecurityUtil.getCurrentUserId();
        VolunteerProfile volunteer = volunteerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Volunteer profile not found for user ID: " + userId));

        User user = volunteer.getUser();
        UserValidationUtil.checkApproved(user);
        UserValidationUtil.checkRole(user, "VOLUNTEER");

        return volunteer;
    }

    private VolunteerProfileDTO mapToDTO(VolunteerProfile v) {
        return VolunteerProfileDTO.builder()
                .id(v.getId())
                .username(v.getUser().getUsername())
                .email(v.getUser().getEmail())
                .phoneNumber(v.getUser().getPhoneNumber())
                .availability(v.getAvailability())
                .skills(v.getSkills())
                .build();
    }

    private TaskDTO mapToTaskDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .status(task.getStatus())
                .assignedDate(task.getAssignedDate())
                .dueDate(task.getDueDate())
                .volunteerId(task.getVolunteer() != null ? task.getVolunteer().getId() : null)
                .build();
    }
}
