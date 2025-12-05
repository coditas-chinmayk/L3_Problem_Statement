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
    public ResponseEntity<ApiResponseDto<ViewEmpTasksDto>> createTask(@Valid @RequestBody CreateTaskDto dto) {
        ViewEmpTasksDto task = taskService.createTask(dto);
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
    public ResponseEntity<ApiResponseDto<TaskDto>> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponseDto.ok("Task found", task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TaskDto>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskDto dto) {
        TaskDto updatedTask = taskService.updateTask(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok("Task updated successfully", updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponseDto.ok("Task deleted successfully"));
    }

    @GetMapping("/employees/{empId}/tasks")
    public ResponseEntity<ApiResponseDto<List<TaskDto>>> getTasksForEmployee(@PathVariable Long empId) {
        List<TaskDto> tasks = taskService.getTasksForEmployee(empId);
        return ResponseEntity.ok(ApiResponseDto.ok("Tasks for employee retrieved", tasks));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto<TaskDto>> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusDto dto) {
        TaskDto task = taskService.updateTaskStatus(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok("Task status updated successfully", task));
    }
}