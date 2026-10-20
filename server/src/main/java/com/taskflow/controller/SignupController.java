package com.taskflow.controller;

import com.taskflow.dto.SignupRequest;
import com.taskflow.dto.UserResponse;
import com.taskflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /api/signup
     * Validates signup, hashes password with BCrypt, saves to SQL database, returns safe user payload
     */
    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody SignupRequest request) {
        UserResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
