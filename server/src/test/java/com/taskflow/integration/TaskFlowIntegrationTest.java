package com.taskflow.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.dto.SignupRequest;
import com.taskflow.dto.TaskRequest;
import com.taskflow.repository.TaskRepository;
import com.taskflow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TaskFlowIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDatabase() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("End-to-End: Save Task to SQL Database and Retrieve via GET /api/tasks")
    void testSaveAndRetrieveTask() throws Exception {
        TaskRequest request = new TaskRequest(
                "Study Year 1 Term 2 Diploma",
                "Learn Spring Boot and SQL relational architecture",
                "High",
                LocalDate.of(2026, 12, 10)
        );

        // 1. POST /api/tasks
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Study Year 1 Term 2 Diploma")))
                .andExpect(jsonPath("$.priority", is("High")));

        // Verify persisted in SQL database
        assertThat(taskRepository.count()).isEqualTo(1);

        // 2. GET /api/tasks
        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Study Year 1 Term 2 Diploma")));
    }

    @Test
    @DisplayName("End-to-End: Register User in SQL Database, Password Hashed with BCrypt")
    void testRegisterUserEndToEnd() throws Exception {
        SignupRequest request = new SignupRequest("John Student", "student@example.com", "superSecret999");

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("student@example.com")))
                .andExpect(jsonPath("$.fullName", is("John Student")));

        // Check SQL database record
        var userInDb = userRepository.findByEmail("student@example.com");
        assertThat(userInDb).isPresent();
        assertThat(userInDb.get().getPasswordHash()).startsWith("$2a$");
        assertThat(userInDb.get().getPasswordHash()).isNotEqualTo("superSecret999");
    }
}
