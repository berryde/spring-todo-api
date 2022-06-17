package com.example.springtodoapi.controller;

import com.example.springtodoapi.entity.Todo;
import com.example.springtodoapi.dto.TodoDto;

import com.example.springtodoapi.service.TodoService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {

  private static final String ERR_TASK_NOT_FOUND = "task not found";
  private TodoService todoService;


  @Autowired
  public TodoController(TodoService todoService) {
    this.todoService = todoService;

  }

  public Todo mapToTodo(TodoDto dto) {
    return new Todo(dto.getId(), dto.getText(), dto.isCompleted());
  }

  public TodoDto mapToTodoDto(Todo todo) {
    TodoDto dto = new TodoDto();
    dto.setId(todo.getId());
    dto.setText(todo.getText());
    dto.setCompleted(todo.isCompleted());
    return dto;
  }

  // HTTP GET ALL
  @GetMapping("/tasks/")
  public List<TodoDto> getTasks() {
    return this.todoService.findAll().stream().map(this::mapToTodoDto).toList();
  }

  // HTTP GET
  @GetMapping("/tasks/{id}")
  public TodoDto getTaskById(@PathVariable String id) {
    try {
      return mapToTodoDto(this.todoService.findById(id));
    } catch (RuntimeException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_TASK_NOT_FOUND);
    }
  }

  // HTTP POST
  @PostMapping("/tasks/")
  public TodoDto addTask(@RequestBody TodoDto dto) {
    dto.setId(UUID.randomUUID().toString());
    return mapToTodoDto(this.todoService.save(mapToTodo(dto)));
  }

  // HTTP PUT
  @PutMapping("/tasks/{id}")
  public TodoDto updateTask(@PathVariable String id, @RequestBody TodoDto dto) {
    dto.setId(id);
    try {
      return mapToTodoDto(this.todoService.update(mapToTodo(dto)));
    } catch (RuntimeException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_TASK_NOT_FOUND);
    }
  }

  // HTTP DELETE
  @DeleteMapping("/tasks/{id}")
  public void deleteTaskById(@PathVariable String id) {
    try {
      this.todoService.deleteById(id);
    } catch (RuntimeException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERR_TASK_NOT_FOUND);
    }
  }
}
