package com.example.springtodoapi.entity;


import com.example.springtodoapi.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TodoTests {

  Todo todo;

  @BeforeEach
  void setUp() {
    todo = new Todo("aff5e31a-fd9a-45b2-a8dc-ccc39c0242c3", "Buy petrol for car", false);
  }

  @Test
  void getsId() {
    assertEquals("aff5e31a-fd9a-45b2-a8dc-ccc39c0242c3", todo.getId());
  }

  @Test
  void setsId() {
    todo.setId("91da625b-4b4b-4634-bc16-8f55eef6907b");
    assertEquals("91da625b-4b4b-4634-bc16-8f55eef6907b", todo.getId());
  }

  @Test
  void getsText() {
    assertEquals("Buy petrol for car", todo.getText());
  }

  @Test
  void setsText() {
    todo.setText("Inflate car tires");
    assertEquals("Inflate car tires", todo.getText());
  }

  @Test
  void getsCompleted() {
    assertFalse(todo.isCompleted());
  }

  @Test
  void setsCompleted() {
    todo.setCompleted(true);
    assertTrue(todo.isCompleted());
  }
}
