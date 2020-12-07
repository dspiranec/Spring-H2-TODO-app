package com.dspiranec.todo.controller;

import com.dspiranec.todo.command.TaskCommand;
import com.dspiranec.todo.dto.TaskDTO;
import com.dspiranec.todo.exception.TaskNotFoundException;
import com.dspiranec.todo.service.TaskService;
import com.dspiranec.todo.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getAllTasks(){
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findTaskById(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(new ApiResponse(taskService.findTaskById(id)), HttpStatus.OK);
        }
        catch(TaskNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(@RequestBody @Valid final TaskCommand taskCommand) {
        try {
            return new ResponseEntity<>(new ApiResponse(taskService.createTask(taskCommand)), HttpStatus.CREATED);
        }
        catch(TaskNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable final Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        }
        catch(TaskNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<ApiResponse> updateTask(@RequestBody @Valid final TaskCommand taskCommand){
        try {
            return new ResponseEntity<>(new ApiResponse(taskService.updateTask(taskCommand)), HttpStatus.OK);
        }
        catch(TaskNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
}
