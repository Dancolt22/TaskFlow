package com.taskflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {

    @NotBlank(message = "Fill in your name, email and password.")
    private String fullName;

    @NotBlank(message = "Fill in your name, email and password.")
    @Email(message = "Please provide a valid email address.")
    private String email;

    @NotBlank(message = "Fill in your name, email and password.")
    @Size(min = 8, message = "Password must be at least 8 characters.")
    private String password;

    public SignupRequest() {}

    public SignupRequest(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
