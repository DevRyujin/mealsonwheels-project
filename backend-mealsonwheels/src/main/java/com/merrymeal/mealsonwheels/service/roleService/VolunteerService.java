package com.merrymeal.mealsonwheels_backend.service.roleService;

import com.merrymeal.mealsonwheels.dto.TaskDTO;
import com.merrymeal.mealsonwheels_backend.dto.roleDTOs.VolunteerDTO;

import java.util.List;

public interface VolunteerService {
    VolunteerDTO getVolunteerProfile();
    List<TaskDTO> getAssignedTasks();
}
