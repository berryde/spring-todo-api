package com.example.springtodoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.springtodoapi.dao.TodoRepository;
import com.example.springtodoapi.entity.Todo;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TodoServiceImplTests {

  @Autowired
  private TodoService todoService;

  @MockBean
  private TodoRepository todoRepository;

  private Todo todoMock;

  @BeforeEach
  void setUp() {
    todoMock = new Todo("85704e65-e2c3-4fff-8324-c0c5a4e5bfa9", "Go for a swim", false);
  }

  @Test
  void contextLoads() {
    assertNotNull(todoService);
    assertNotNull(todoRepository);
  }

  @Test
  void shouldGetTasks() {
    final Iterable<Todo> todos = Collections.singletonList(todoMock);
    when(todoRepository.findAll()).thenReturn(todos);
    assertEquals(todos, todoService.findAll());
  }

  @Test
  void shouldGetTaskById() {
    when(todoRepository.findById(todoMock.getId())).thenReturn(Optional.of(todoMock));
    assertEquals(todoMock, todoService.findById(todoMock.getId()));
  }

  @Test
  void shouldThrowWhenTaskDoesNotExist() {
    when(todoRepository.findById(todoMock.getId())).thenReturn(Optional.empty());
    String id = todoMock.getId();
    assertThrows(RuntimeException.class, () -> {
      todoService.findById(id);
    });
  }

  @Test
  void shouldSaveTask() {
    when(todoRepository.save(todoMock)).thenReturn(todoMock);
    assertEquals(todoMock, todoService.save(todoMock));
  }
}
