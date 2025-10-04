package com.example.backendStudyExample.task.dto;

import com.example.backendStudyExample.task.domain.Task;
import com.example.backendStudyExample.task.domain.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status
) {
    public static TaskResponseDto from(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus()
        );
    }
}
