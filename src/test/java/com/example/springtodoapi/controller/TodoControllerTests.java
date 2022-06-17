package com.example.springtodoapi.controller;

import com.example.springtodoapi.dao.TodoRepository;
import com.example.springtodoapi.entity.Todo;
import com.example.springtodoapi.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTests {

  @MockBean
  private TodoService todoService;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  private Todo todoMock;

  @BeforeEach
  void setUp() {
    final String id = "1d268edd-2796-4348-8228-7ce7a485749d";
    final String text = "Extend parking";
    todoMock = new Todo(id, text, false);
  }

  @Test
  void contextLoads() {
    assertThat(mockMvc).isNotNull();
  }

  @Test
  void shouldGetTasks() throws Exception {
    when(this.todoService.findAll()).thenReturn(List.of(todoMock));
    mockMvc.perform(get("/tasks/")).andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].id").value(todoMock.getId()))
        .andExpect(jsonPath("$[0].text").value(todoMock.getText()))
        .andExpect(jsonPath("$[0].completed").value(todoMock.isCompleted()));
  }

  @Test
  void shouldGetTaskById() throws Exception {
    when(this.todoService.findById(todoMock.getId())).thenReturn(todoMock);
    mockMvc.perform(get("/tasks/" + todoMock.getId())).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(todoMock.getId()))
        .andExpect(jsonPath("$.text").value(todoMock.getText()))
        .andExpect(jsonPath("$.completed").value(todoMock.isCompleted()));
  }

  @Test
  void shouldAddTask() throws Exception {
    when(this.todoService.save(any(Todo.class))).thenReturn(todoMock);
    mockMvc.perform(post("/tasks/").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(todoMock))).andExpect(status().isOk());
  }

  @Test
  void shouldUpdateTaskIfExists() throws Exception {
    when(this.todoService.update(any(Todo.class))).thenReturn(todoMock);
    mockMvc.perform(put("/tasks/" + todoMock.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(todoMock))).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(todoMock.getId()))
        .andExpect(jsonPath("$.text").value(todoMock.getText()))
        .andExpect(jsonPath("$.completed").value(todoMock.isCompleted()));
  }

  @Test
  void shouldNotUpdateTaskIfMissing() throws Exception {
    when(this.todoService.update(any(Todo.class))).thenThrow(new RuntimeException("task not found"));

    mockMvc.perform(put("/tasks/" + todoMock.getId()).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(todoMock))).andExpect(status().isNotFound());
  }
}
