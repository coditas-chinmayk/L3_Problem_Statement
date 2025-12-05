package com.example.L3.service;

import com.example.L3.constant.TaskStatus;
import com.example.L3.dto.*;
import com.example.L3.entity.Employee;
import com.example.L3.entity.Task;
import com.example.L3.repository.EmployeeRepository;
import com.example.L3.repository.TaskRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Data
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @Transactional
    public ViewEmpTasksDto createTask(CreateTaskDto dto) {
        Employee emp = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));

        TaskStatus status = TaskStatus.valueOf(dto.getStatus().toUpperCase());

        Task task = new Task();
        task.setDescription(dto.getDescription());
        task.setDeadline(LocalDate.parse(dto.getDeadline()));
        task.setStatus(status);
        task.setAssignedTo(emp);

        return  this.viewEmpTasksDto(taskRepository.save(task), emp);
    }

    public TaskDto assignTaskToEmp(Long taskId, Long empId){
        Employee employee = employeeRepository.findById(empId).orElseThrow(()-> new NoSuchElementException("invalid employee Id"));
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new NoSuchElementException("invalid task Id"));
        task.setAssignedTo(employee);
        return this.toTaskDto(taskRepository.save(task));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        return this.toTaskDto(task);
    }

    public TaskDto updateTask(Long taskId, UpdateTaskDto dto) throws BadRequestException {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        if(!Objects.equals(task.getId(), taskId)) throw new BadRequestException("this task does not belong to you");

        task.setDescription(dto.getDescription() != null ? dto.getDescription() : task.getDescription());
        task.setDeadline(dto.getDeadline() != null ? LocalDate.parse(dto.getDeadline()) : task.getDeadline());
        task.setStatus(dto.getStatus() != null ? TaskStatus.valueOf(dto.getStatus().toUpperCase()) : task.getStatus());

        return this.toTaskDto(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Task not found"));
        taskRepository.deleteById(id);
    }

    public List<TaskDto> getTasksForEmployee(Long empId) {
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        return emp.getTasks().stream().map(this::toTaskDto).toList();
    }

    public TaskDto updateTaskStatus(Long id, UpdateTaskStatusDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        task.setStatus(TaskStatus.valueOf(dto.getStatus().toUpperCase()));
        return this.toTaskDto(taskRepository.save(task));
    }

    public ViewEmpTasksDto viewEmpTasksDto(Task task, Employee emp){
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        return ViewEmpTasksDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .tasks(taskList)
                .email(emp.getEmail())
                .build();
    }

    public TaskDto toTaskDto(Task task){
        return TaskDto.builder()
                .id(task.getId().toString())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(task.getStatus().name())
                .employeeId(task.getAssignedTo().getId().toString())
                .build();

    }

}
