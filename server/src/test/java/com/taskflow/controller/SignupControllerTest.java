package com.taskflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.dto.SignupRequest;
import com.taskflow.dto.UserResponse;
import com.taskflow.entity.User;
import com.taskflow.exception.ConflictException;
import com.taskflow.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignupController.class)
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/signup returns 201 Created on valid registration")
    void testSignupSuccess() throws Exception {
        SignupRequest request = new SignupRequest("Jane Doe", "jane@example.com", "securePassword123");

        User savedUser = new User("Jane Doe", "jane@example.com", "$2a$10$hashedstring");
        savedUser.setId(505L);

        when(userService.registerUser(any(SignupRequest.class))).thenReturn(new UserResponse(savedUser));

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(505)))
                .andExpect(jsonPath("$._id", is("505")))
                .andExpect(jsonPath("$.fullName", is("Jane Doe")))
                .andExpect(jsonPath("$.email", is("jane@example.com")))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.passwordHash").doesNotExist());
    }

    @Test
    @DisplayName("POST /api/signup returns 400 Bad Request when password is under 8 characters")
    void testSignupShortPassword() throws Exception {
        SignupRequest request = new SignupRequest("Jane Doe", "jane@example.com", "short");

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Password must be at least 8 characters.")));
    }

    @Test
    @DisplayName("POST /api/signup returns 409 Conflict when email already registered")
    void testSignupDuplicateEmail() throws Exception {
        SignupRequest request = new SignupRequest("Jane Doe", "existing@example.com", "validPassword123");

        when(userService.registerUser(any(SignupRequest.class)))
                .thenThrow(new ConflictException("Email is already registered."));

        mockMvc.perform(post("/api/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Email is already registered.")));
    }
}
