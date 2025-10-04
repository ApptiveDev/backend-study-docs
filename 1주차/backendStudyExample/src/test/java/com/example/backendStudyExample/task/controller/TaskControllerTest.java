package com.example.backendStudyExample.task.controller;

import com.example.backendStudyExample.task.domain.Task;
import com.example.backendStudyExample.task.dto.NewTaskRequestDto;
import com.example.backendStudyExample.task.dto.TaskUpdateRequestDto;
import com.example.backendStudyExample.task.service.TaskService;
import com.example.backendStudyExample.util.TestDtoUtil;
import com.example.backendStudyExample.util.TestEntityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void createTask() throws Exception {
        // given
        NewTaskRequestDto requestDto = TestDtoUtil.makeNewTaskRequestDto();
        Task createdTask = TestEntityUtil.makeTaskWithId(1L);
        given(taskService.createTask(any(NewTaskRequestDto.class))).willReturn(createdTask);

        // when & then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("test title"));
    }

    @Test
    void getTask() throws Exception {
        // given
        Long taskId = 1L;
        Task task = TestEntityUtil.makeTaskWithId(taskId);

        given(taskService.getTask(taskId)).willReturn(task);

        // when & then
        mockMvc.perform(get("/api/tasks/" + taskId))
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("test title"));
    }

    @Test
    void getTaskList() throws Exception {
        // given
        Task task1 = TestEntityUtil.makeTaskWithId(1L);
        Task task2 = TestEntityUtil.makeTaskWithId(2L);

        given(taskService.getTaskList()).willReturn(List.of(task1, task2));

        // when
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void updateTask() throws Exception {
        Long taskId = 1L;
        TaskUpdateRequestDto requestDto = TestDtoUtil.makeTaskUpdateRequestDto();
        Task task = TestEntityUtil.makeTaskWithIdAndTitle(taskId, "updated title");

        doNothing().when(taskService).updateTask(eq(taskId), any(TaskUpdateRequestDto.class));

        given(taskService.getTask(taskId)).willReturn(task);

        // when & then
        mockMvc.perform(patch("/api/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId))
                .andExpect(jsonPath("$.title").value("updated title"));
    }

    @Test
    void deleteTaskTest() throws Exception {
        // given
        Long taskId = 1L;
        doNothing().when(taskService).deleteTask(taskId);

        // when & then
        mockMvc.perform(delete("/api/tasks/" + taskId))
                .andExpect(status().isNoContent());
    }
}
