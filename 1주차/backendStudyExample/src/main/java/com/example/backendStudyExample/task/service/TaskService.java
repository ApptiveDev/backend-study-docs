package com.example.backendStudyExample.task.service;

import com.example.backendStudyExample.global.exception.EntityNotFoundException;
import com.example.backendStudyExample.task.domain.Task;
import com.example.backendStudyExample.task.dto.NewTaskRequestDto;
import com.example.backendStudyExample.task.dto.TaskUpdateRequestDto;
import com.example.backendStudyExample.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task createTask(NewTaskRequestDto requestDto) {
        Task newTask = new Task(
                requestDto.title(),
                requestDto.description(),
                requestDto.dueDate(),
                requestDto.status()
        );

        return taskRepository.save(newTask);
    }

    @Transactional(readOnly = true)
    public Task getTask(Long taskId) {
        return findTaskById(taskId);
    }

    @Transactional(readOnly = true)
    public List<Task> getTaskList() {
        return taskRepository.findAll();
    }

    @Transactional
    public void updateTask(Long taskId, TaskUpdateRequestDto requestDto) {
        Task foundTask = findTaskById(taskId);

        foundTask.update(
                requestDto.title(),
                requestDto.description(),
                requestDto.dueDate(),
                requestDto.status()
        );
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task foundTask = findTaskById(taskId);

        taskRepository.delete(foundTask);
    }

    private Task findTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정은 존재하지 않습니다."));
    }
}
