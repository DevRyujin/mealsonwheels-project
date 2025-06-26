package com.merrymeal.mealsonwheels.service.adminService;

import com.merrymeal.mealsonwheels.dto.TaskDTO;
import com.merrymeal.mealsonwheels.exception.ResourceNotFoundException;
import com.merrymeal.mealsonwheels.model.Task;
import com.merrymeal.mealsonwheels.model.TaskStatus;
import com.merrymeal.mealsonwheels.model.User;
import com.merrymeal.mealsonwheels.model.VolunteerProfile;
import com.merrymeal.mealsonwheels.repository.TaskRepository;
import com.merrymeal.mealsonwheels.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        User volunteerUser = userRepository.findById(taskDTO.getVolunteerId())
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id " + taskDTO.getVolunteerId()));

        Task task = Task.builder()
                .description(taskDTO.getDescription())
                .status(parseStatus(taskDTO.getStatus()))
                .volunteer(volunteerUser)
                .build();

        taskRepository.save(task);
        return convertToDTO(task);
    }



    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        task.setDescription(taskDTO.getDescription());
        task.setStatus(parseStatus(taskDTO.getStatus()));

        if (!task.getVolunteer().getId().equals(taskDTO.getVolunteerId())) {
            User newVolunteer = userRepository.findById(taskDTO.getVolunteerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id " + taskDTO.getVolunteerId()));
            task.setVolunteer(newVolunteer);
        }

        return convertToDTO(taskRepository.save(task));
    }


    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByVolunteerId(Long volunteerId) {
        User volunteer = userRepository.findById(volunteerId)
                .orElseThrow(() -> new ResourceNotFoundException("Volunteer not found with id " + volunteerId));
        return taskRepository.findByVolunteer(volunteer).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus().name())               // For backend use
                .statusLabel(task.getStatus().getLabel())      // For frontend dropdown display
                .volunteerId(task.getVolunteer().getId())
                .volunteerName(task.getVolunteer().getName())
                .build();
    }

    private TaskStatus parseStatus(String statusStr) {
        try {
            return TaskStatus.valueOf(statusStr.toUpperCase());  // Converts "Pending" → "PENDING"
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid task status: " + statusStr);
        }
    }

}
