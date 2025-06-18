package com.merrymeal.mealsonwheels_backend.controller;

import com.merrymeal.mealsonwheels_backend.dto.TaskDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.VolunteerDTO;
import com.merrymeal.mealsonwheels_backend.service.roleService.VolunteerService;
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
    public ResponseEntity<VolunteerDTO> getProfile() {
        return ResponseEntity.ok(volunteerService.getVolunteerProfile());
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getAssignedTasks() {
        return ResponseEntity.ok(volunteerService.getAssignedTasks());
    }
}
