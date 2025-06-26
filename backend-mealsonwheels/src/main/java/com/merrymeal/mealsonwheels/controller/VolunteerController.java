package com.merrymeal.mealsonwheels.controller;

import com.merrymeal.mealsonwheels.dto.TaskDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.VolunteerProfileDTO;
import com.merrymeal.mealsonwheels.service.roleService.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService volunteerService;

    @GetMapping("/profile")
    public ResponseEntity<VolunteerProfileDTO> getProfile() {
        return ResponseEntity.ok(volunteerService.getVolunteerProfile());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAssignedTasks() {
        return ResponseEntity.ok(volunteerService.getAssignedTasks());
    }
}
