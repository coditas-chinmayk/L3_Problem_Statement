package com.example.L3.controller;

import com.example.L3.dto.*;
import com.example.L3.entity.Task;
import com.example.L3.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<Task>> createTask(@Valid @RequestBody CreateTaskDto dto) {
        Task task = taskService.createTask(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok("Task created successfully", task));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Task>>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(ApiResponseDto.ok("Tasks retrieved successfully"+ tasks+ ", tasks"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Task>> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponseDto.ok("Task found", task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Task>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskDto dto) {
        Task updatedTask = taskService.updateTask(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok("Task updated successfully", updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponseDto.ok("Task deleted successfully"));
    }

    @GetMapping("/employees/{empId}/tasks")
    public ResponseEntity<ApiResponseDto<List<Task>>> getTasksForEmployee(@PathVariable Long empId) {
        List<Task> tasks = taskService.getTasksForEmployee(empId);
        return ResponseEntity.ok(ApiResponseDto.ok("Tasks for employee retrieved", tasks));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto<Task>> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusDto dto) {
        Task task = taskService.updateTaskStatus(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok("Task status updated successfully", task));
    }
}