package com.taskflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskflow.entity.User;
import java.time.Instant;

public class UserResponse {

    private Long id;

    @JsonProperty("_id")
    private String mongoCompatibleId;

    private String fullName;
    private String email;
    private Instant createdAt;

    public UserResponse() {}

    public UserResponse(User user) {
        this.id = user.getId();
        this.mongoCompatibleId = user.getId() != null ? String.valueOf(user.getId()) : null;
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.mongoCompatibleId = id != null ? String.valueOf(id) : null;
    }

    public String getMongoCompatibleId() {
        return mongoCompatibleId;
    }

    public void setMongoCompatibleId(String mongoCompatibleId) {
        this.mongoCompatibleId = mongoCompatibleId;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
