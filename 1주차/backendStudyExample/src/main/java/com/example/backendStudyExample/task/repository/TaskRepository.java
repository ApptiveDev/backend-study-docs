package com.example.backendStudyExample.task.repository;

import com.example.backendStudyExample.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
