package com.example.springtodoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class TodoController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // HTTP GET ALL
    @GetMapping("/tasks/")
    public Iterable<Todo> getTasks() {
        return this.todoRepository.findAll();
    }

    // HTTP GET
    @GetMapping("/tasks/{id}")
    public Todo getTaskById(@PathVariable String id) {
        Optional<Todo> todo = this.todoRepository.findById(id);
        if (todo.isPresent()) return todo.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found");
    }

    // HTTP POST
    @PostMapping("/tasks/")
    public void addTask(@RequestBody Todo todo) {
        todo.setId(UUID.randomUUID().toString());
        this.todoRepository.save(todo);
    }

    // HTTP PUT
    @PutMapping("/tasks/{id}")
    public void updateTask(@PathVariable String id, @RequestBody Todo todo) {
        if (!this.todoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found");
        }
        todo.setId(id);
        this.todoRepository.save(todo);
    }

    // HTTP DELETE
    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(String id) {
        if (!this.todoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found");
        }
        this.todoRepository.deleteById(id);
    }
}
