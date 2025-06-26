package com.merrymeal.mealsonwheels.service.admin;

import com.merrymeal.mealsonwheels.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
    List<TaskDTO> getAllTasks();
    List<TaskDTO> getTasksByVolunteerId(Long volunteerId);
}
