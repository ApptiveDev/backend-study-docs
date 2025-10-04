package com.example.backendStudyExample.util;

import com.example.backendStudyExample.task.domain.TaskStatus;
import com.example.backendStudyExample.task.dto.NewTaskRequestDto;
import com.example.backendStudyExample.task.dto.TaskUpdateRequestDto;

import java.time.LocalDateTime;

public class TestDtoUtil {

    public static NewTaskRequestDto makeNewTaskRequestDto() {
        return new NewTaskRequestDto(
                "test title",
                "test description",
                LocalDateTime.of(2025, 10, 1, 10, 0, 0),
                TaskStatus.TODO
        );
    }

    public static TaskUpdateRequestDto makeTaskUpdateRequestDto() {
        return new TaskUpdateRequestDto(
                "modified title",
                "modified description",
                null,
                TaskStatus.COMPLETED
        );
    }
}
