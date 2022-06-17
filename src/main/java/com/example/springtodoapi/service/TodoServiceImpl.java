package com.example.springtodoapi.service;

import com.example.springtodoapi.dao.TodoRepository;
import com.example.springtodoapi.entity.Todo;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Autowired
  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  public List<Todo> findAll() {
    return StreamSupport.stream(todoRepository.findAll().spliterator(), false).toList();
  }

  @Override
  public Todo findById(String id) {
    Optional<Todo> task = todoRepository.findById(id);
    if (!task.isPresent()) {
      throw new RuntimeException("task not found: " + id);
    }
    return task.get();
  }

  @Override
  public Todo save(Todo todo) {
    return todoRepository.save(todo);
  }

  @Override
  public void deleteById(String id) {
    todoRepository.deleteById(id);
  }

  @Override
  public boolean existsById(String id) {
    return todoRepository.existsById(id);
  }

  @Override
  public Todo update(Todo todo) {
    if (todoRepository.existsById(todo.getId())) {
      return todoRepository.save(todo);
    } else {
      throw new RuntimeException("task not found: " + todo.getId());
    }

  }


}
