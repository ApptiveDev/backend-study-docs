package com.example.backendStudyExample.task.dto;

import com.example.backendStudyExample.task.domain.TaskStatus;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskUpdateRequestDto(
        @Size(max = 50, message = "제목은 최대 50자입니다.")
        String title,
        String description,
        LocalDateTime dueDate,
        TaskStatus status
) {
}
