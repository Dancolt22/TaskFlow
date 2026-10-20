package com.taskflow.service;

import com.taskflow.dto.TaskRequest;
import com.taskflow.dto.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retrieve all tasks ordered newest first
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Create and persist a new task in the SQL database
     */
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task(
                request.getTitle().trim(),
                request.getDescription().trim(),
                request.getPriority().trim(),
                request.getDueDate()
        );

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }
}
