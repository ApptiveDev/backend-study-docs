package com.example.backendStudyExample.task.service;

import com.example.backendStudyExample.global.exception.EntityNotFoundException;
import com.example.backendStudyExample.task.domain.Task;
import com.example.backendStudyExample.task.domain.TaskStatus;
import com.example.backendStudyExample.task.dto.NewTaskRequestDto;
import com.example.backendStudyExample.task.dto.TaskUpdateRequestDto;
import com.example.backendStudyExample.task.repository.TaskRepository;
import com.example.backendStudyExample.util.TestDtoUtil;
import com.example.backendStudyExample.util.TestEntityUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("새로 할 일 추가")
    void createTask() {
        // given
        NewTaskRequestDto requestDto = TestDtoUtil.makeNewTaskRequestDto();
        Task task = TestEntityUtil.makeTaskWithId(1L);

        given(taskRepository.save(any(Task.class))).willReturn(task);

        // when
        Task createdTask = taskService.createTask(requestDto);

        // then
        assertThat(createdTask.getId()).isEqualTo(1L);
        assertThat(createdTask.getTitle()).isEqualTo("test title");

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("ID로 조회")
    void getTask() {
        // given
        Long taskId = 1L;
        Task task = TestEntityUtil.makeTaskWithId(taskId);

        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        // when
        Task foundTask = taskService.getTask(taskId);

        // then
        assertThat(foundTask.getTitle()).isEqualTo("test title");
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회")
    void getTaskFail() {
        Long taskId = 99L;
        given(taskRepository.findById(taskId)).willReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            taskService.getTask(taskId);
        });
    }

    @Test
    @DisplayName("할 일 목록 조회")
    void getTaskList() {
        // given
        Task task1 = TestEntityUtil.makeTaskWithId(1L);
        Task task2 = TestEntityUtil.makeTaskWithId(2L);

        given(taskRepository.findAll()).willReturn(List.of(task1, task2));

        // when
        List<Task> taskList = taskService.getTaskList();

        // then
        assertThat(taskList).hasSize(2);
        assertThat(taskList.get(0).getTitle()).isEqualTo("test title");
    }

    @Test
    @DisplayName("할 일 수정")
    void updateTask() {
        Long taskId = 1L;
        Task existingTask = TestEntityUtil.makeTaskWithId(taskId);
        TaskUpdateRequestDto requestDto = TestDtoUtil.makeTaskUpdateRequestDto();

        given(taskRepository.findById(taskId)).willReturn(Optional.of(existingTask));

        // when
        taskService.updateTask(taskId, requestDto);

        // then
        assertThat(existingTask.getTitle()).isEqualTo("modified title");
        assertThat(existingTask.getDescription()).isEqualTo("modified description");
        assertThat(existingTask.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }

    @Test
    @DisplayName("할 일 삭제")
    void deleteTask() {
        // given
        Long taskId = 1L;
        Task task = TestEntityUtil.makeTaskWithId(taskId);
        given(taskRepository.findById(taskId)).willReturn(Optional.of(task));

        // when
        taskService.deleteTask(taskId);

        // then
        verify(taskRepository).delete(task);
    }
}
