package com.example.springtodoapi.service;

import com.example.springtodoapi.entity.Todo;
import java.util.List;

public interface TodoService {

  List<Todo> findAll();

  Todo findById(String id);

  Todo save(Todo todo);

  void deleteById(String id);

  boolean existsById(String id);

  Todo update(Todo todo);
}
