package com.taskflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskflow.entity.Task;
import java.time.Instant;
import java.time.LocalDate;

public class TaskResponse {

    private Long id;

    @JsonProperty("_id")
    private String mongoCompatibleId;

    private String title;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private Instant createdAt;
    private Instant updatedAt;

    public TaskResponse() {}

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.mongoCompatibleId = task.getId() != null ? String.valueOf(task.getId()) : null;
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.dueDate = task.getDueDate();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
