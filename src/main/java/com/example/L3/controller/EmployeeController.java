package com.example.L3.controller;

import com.example.L3.dto.*;
import com.example.L3.entity.Employee;
import com.example.L3.repository.EmployeeRepository;
import com.example.L3.repository.RoleRepository;
import com.example.L3.repository.TaskRepository;
import com.example.L3.security.AuthService;
import com.example.L3.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(
            @RequestBody @Valid LoginRequestDto loginRequest) {

        LoginResponseDto dto = authService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        return ResponseEntity.ok(ApiResponseDto.ok("Login Success",dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ViewEmployeeDto>>> getAll(){
        List<ViewEmployeeDto> employeeList = employeeService.getAll();
        return ResponseEntity.ok(ApiResponseDto.ok("all employees",employeeList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ViewEmployeeDto>> toViewEmpDto(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponseDto.ok("employee created", employeeService.getById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<ViewEmployeeDto>> createEmployee(CreateEmployeeDto dto) throws BadRequestException {
        return ResponseEntity.ok(ApiResponseDto.ok("employee created", employeeService.createEmployee(dto)));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ApiResponseDto<ViewEmployeeDto>> updateEmployee(UpdateEmpDto dto){
        return ResponseEntity.ok(ApiResponseDto.ok("employee created", employeeService.updateEmp(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteEmployee(@PathVariable Long id){
        return ResponseEntity.ok(ApiResponseDto.ok("User deleted"));
    }

}
