package com.merrymeal.mealsonwheels_backend.service.adminService;

import com.merrymeal.mealsonwheels_backend.dto.TaskDTO;
import com.merrymeal.mealsonwheels_backend.model.Task;
import com.merrymeal.mealsonwheels_backend.model.Volunteer;
import com.merrymeal.mealsonwheels_backend.repository.TaskRepository;
import com.merrymeal.mealsonwheels_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Volunteer volunteer = (Volunteer) userRepository.findById(taskDTO.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        Task task = Task.builder()
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus())
                .volunteer(volunteer)
                .build();

        taskRepository.save(task);
        return convertToDTO(task);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());

        if (!task.getVolunteer().getId().equals(taskDTO.getVolunteerId())) {
            Volunteer volunteer = (Volunteer) userRepository.findById(taskDTO.getVolunteerId())
                    .orElseThrow(() -> new RuntimeException("Volunteer not found"));
            task.setVolunteer(volunteer);
        }

        return convertToDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
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
        Volunteer volunteer = (Volunteer) userRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));
        return taskRepository.findByVolunteer(volunteer).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .description(task.getDescription())
                .status(task.getStatus())
                .volunteerId(task.getVolunteer().getId())
                .volunteerName(task.getVolunteer().getUsername())
                .build();
    }
}
