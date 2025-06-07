package com.papaja.papaja;

import com.papaja.papaja.model.Task;
import com.papaja.papaja.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> all() {
        return service.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return service.save(task);
    }
}