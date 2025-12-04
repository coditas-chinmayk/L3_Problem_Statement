package com.example.L3.service;

import com.example.L3.constant.TaskStatus;
import com.example.L3.dto.CreateTaskDto;
import com.example.L3.dto.UpdateTaskDto;
import com.example.L3.dto.UpdateTaskStatusDto;
import com.example.L3.entity.Employee;
import com.example.L3.entity.Task;
import com.example.L3.repository.EmployeeRepository;
import com.example.L3.repository.TaskRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Async
    public Task createTask(CreateTaskDto dto) {
        Employee emp = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        TaskStatus status = TaskStatus.valueOf(dto.getStatus().toUpperCase());

        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setDeadline(LocalDate.parse(dto.getDeadline()));
        task.setStatus(status);
        task.setAssignedTo(emp);

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    public Task updateTask(Long id, UpdateTaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));


        task.setDescription(dto.getDescription() != null ? dto.getDescription() : task.getDescription());
        task.setDeadline(dto.getDeadline() != null ? LocalDate.parse(dto.getDeadline()) : task.getDeadline());
        task.setStatus(dto.getStatus() != null ? TaskStatus.valueOf(dto.getStatus().toUpperCase()) : task.getStatus());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task not found"));
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksForEmployee(Long empId) {
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        return emp.getTasks();
    }

    public Task updateTaskStatus(Long id, UpdateTaskStatusDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.setStatus(TaskStatus.valueOf(dto.getStatus().toUpperCase()));
        return taskRepository.save(task);
    }

}
