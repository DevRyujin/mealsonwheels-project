package com.merrymeal.mealsonwheels.service.roleService;

import com.merrymeal.mealsonwheels.dto.TaskDTO;
import com.merrymeal.mealsonwheels.dto.roleDTOs.VolunteerProfileDTO;

import java.util.List;

public interface VolunteerService {
    VolunteerProfileDTO getVolunteerProfile();
    List<TaskDTO> getAssignedTasks();
}
