package com.taskflow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class TaskRequest {

    @NotBlank(message = "Fill in the title, description and due date.")
    private String title;

    @NotBlank(message = "Fill in the title, description and due date.")
    private String description;

    @NotBlank(message = "Priority is required.")
    @Pattern(regexp = "^(Low|Medium|High)$", message = "Priority must be Low, Medium, or High.")
    private String priority;

    @NotNull(message = "Fill in the title, description and due date.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    public TaskRequest() {}

    public TaskRequest(String title, String description, String priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
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
}
