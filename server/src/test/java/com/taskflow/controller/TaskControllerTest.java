package com.taskflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.dto.TaskRequest;
import com.taskflow.dto.TaskResponse;
import com.taskflow.entity.Task;
import com.taskflow.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    @DisplayName("GET /api/tasks returns 200 OK and list of tasks with _id compatibility")
    void testGetTasksSuccess() throws Exception {
        Task sampleTask = new Task("Finish Spring Boot", "Complete diploma module", "High", LocalDate.of(2026, 10, 30));
        sampleTask.setId(101L);

        TaskResponse response = new TaskResponse(sampleTask);

        when(taskService.getAllTasks()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(101)))
                .andExpect(jsonPath("$[0]._id", is("101")))
                .andExpect(jsonPath("$[0].title", is("Finish Spring Boot")))
                .andExpect(jsonPath("$[0].priority", is("High")));
    }

    @Test
    @DisplayName("POST /api/tasks returns 201 Created when valid payload is submitted")
    void testCreateTaskSuccess() throws Exception {
        TaskRequest request = new TaskRequest(
                "Build REST API",
                "Implement TaskFlow backend",
                "Medium",
                LocalDate.of(2026, 11, 15)
        );

        Task savedTask = new Task(request.getTitle(), request.getDescription(), request.getPriority(), request.getDueDate());
        savedTask.setId(202L);

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(new TaskResponse(savedTask));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(202)))
                .andExpect(jsonPath("$._id", is("202")))
                .andExpect(jsonPath("$.title", is("Build REST API")))
                .andExpect(jsonPath("$.priority", is("Medium")));
    }

    @Test
    @DisplayName("POST /api/tasks returns 400 Bad Request when required title is missing")
    void testCreateTaskMissingTitle() throws Exception {
        TaskRequest request = new TaskRequest("", "No title provided", "Low", LocalDate.of(2026, 12, 1));

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Fill in the title, description and due date.")));
    }
}
