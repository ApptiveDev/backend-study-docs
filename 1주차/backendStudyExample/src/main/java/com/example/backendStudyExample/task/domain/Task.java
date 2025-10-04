package com.example.backendStudyExample.task.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 50)
    private Long id;

    @Column(nullable = false)
    String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    String description;
    @Column(nullable = false)
    LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TaskStatus status;

    public Task(
            Long id,
            String title,
            String description,
            LocalDateTime dueDate,
            TaskStatus status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Task(
            String title,
            String description,
            LocalDateTime dueDate,
            TaskStatus status
    ) {
        this(
                null,
                title,
                description,
                dueDate,
                status
        );
    }

    protected Task() {

    }

    public void update(String title, String description, LocalDateTime dueDate, TaskStatus status) {
        updateTitle(title);
        updateDescription(description);
        updateDueDate(dueDate);
        updateStatus(status);
    }

    private void updateTitle(String title) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
    }

    private void updateDescription(String description) {
        if (description != null && !description.isBlank()) {
            this.description = description;
        }
    }

    private void updateDueDate(LocalDateTime dueDate) {
        if (dueDate != null) {
            this.dueDate = dueDate;
        }
    }

    private void updateStatus(TaskStatus status) {
        if (status != null) {
            this.status = status;
        }
    }
}
