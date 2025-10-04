package com.example.backendStudyExample.util;

import com.example.backendStudyExample.task.domain.Task;
import com.example.backendStudyExample.task.domain.TaskStatus;

import java.time.LocalDateTime;

public class TestEntityUtil {

    public static Task makeTaskWithId(Long id) {
        return new Task(
                id,
                "test title",
                "test description",
                LocalDateTime.now(),
                TaskStatus.TODO
        );
    }

    public static Task makeTaskWithIdAndTitle(Long id, String title) {
        return new Task(
                id,
                title,
                "test description",
                LocalDateTime.of(2025, 10, 1, 10, 0, 0),
                TaskStatus.TODO
        );
    }
}
