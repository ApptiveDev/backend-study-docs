package com.example.backendStudyExample.task.controller;

import com.example.backendStudyExample.task.domain.Task;
import com.example.backendStudyExample.task.dto.NewTaskRequestDto;
import com.example.backendStudyExample.task.dto.TaskResponseDto;
import com.example.backendStudyExample.task.dto.TaskUpdateRequestDto;
import com.example.backendStudyExample.task.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody NewTaskRequestDto requestDto) {
        Task task = taskService.createTask(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponseDto.from(task));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);

        return ResponseEntity.ok(TaskResponseDto.from(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getTaskList() {
        List<TaskResponseDto> taskList = taskService.getTaskList()
                .stream()
                .map(TaskResponseDto::from)
                .toList();

        return ResponseEntity.ok(taskList);
    }

    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateRequestDto requestDto) {
        taskService.updateTask(taskId, requestDto);

        Task task = taskService.getTask(taskId);
        return ResponseEntity.ok(TaskResponseDto.from(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }
}
