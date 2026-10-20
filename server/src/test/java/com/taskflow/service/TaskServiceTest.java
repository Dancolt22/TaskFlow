package com.taskflow.service;

import com.taskflow.dto.TaskRequest;
import com.taskflow.dto.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Test Title", "Test Description", "Medium", LocalDate.now().plusDays(5));
        sampleTask.setId(1L);
    }

    @Test
    @DisplayName("getAllTasks should return all tasks mapped to TaskResponse in descending order")
    void testGetAllTasks() {
        when(taskRepository.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(sampleTask));

        List<TaskResponse> results = taskService.getAllTasks();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(1L);
        assertThat(results.get(0).getTitle()).isEqualTo("Test Title");
        verify(taskRepository, times(1)).findAllByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("createTask should trim input, save task in SQL repository, and return response")
    void testCreateTask() {
        TaskRequest request = new TaskRequest("  New Task  ", "  Detailed Description  ", "High", LocalDate.now().plusDays(2));

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(99L);
            return task;
        });

        TaskResponse response = taskService.createTask(request);

        assertThat(response.getId()).isEqualTo(99L);
        assertThat(response.getTitle()).isEqualTo("New Task");
        assertThat(response.getDescription()).isEqualTo("Detailed Description");
        assertThat(response.getPriority()).isEqualTo("High");
        verify(taskRepository, times(1)).save(any(Task.class));
    }
}
