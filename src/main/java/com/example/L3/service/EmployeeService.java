package com.example.L3.service;

import com.example.L3.constant.EmployeeRole;
import com.example.L3.dto.*;
import com.example.L3.entity.Employee;
import com.example.L3.entity.Role;
import com.example.L3.repository.EmployeeRepository;
import com.example.L3.repository.RoleRepository;
import com.example.L3.repository.TaskRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Data
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;


    //create
    @Transactional
    public ViewEmployeeDto createEmployee(CreateEmployeeDto createDto) throws BadRequestException {

        if (employeeRepository.findByEmail(createDto.getEmail()).isPresent()) {
            throw new BadRequestException("User with this email Id already exists: " + createDto.getEmail());
        }

        EmployeeRole roleEnum;
        try {
            roleEnum = EmployeeRole.valueOf(createDto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + createDto.getRole());
        }

        Role role = roleRepository.findByRole(roleEnum)
                .orElseThrow(() -> new BadRequestException("Role not found: " + createDto.getRole()));

        Employee emp = Employee.builder()
                .name(createDto.getName())
                .email(createDto.getEmail())
                .password(passwordEncoder.encode(createDto.getPassword()))
                .salary(Long.parseLong(createDto.getSalary()))
                .role(role)
                .department(createDto.getDepartment())
                .build();

        return toViewEmpDto(employeeRepository.save(emp));
    }


    //get all
    public List<ViewEmployeeDto> getAll(){
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(this::toViewEmpDto).toList();
    }

    //getByid
    public ViewEmployeeDto getById(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("No Employee with this id"));
        return toViewEmpDto(employee);
    }

    public ViewEmployeeDto updateEmp(UpdateEmpDto dto){
        Employee emp = employeeRepository.findByEmail(dto.getOldEmail()).orElseThrow(()-> new NoSuchElementException("Old email does not exist in db"));
        emp.setEmail(dto.getNewEmail() != null ? dto.getNewEmail() : emp.getEmail());
        emp.setPassword(dto.getNewPassword() != null ? dto.getNewPassword() : emp.getPassword());
        emp.setName(dto.getName() != null ? dto.getName() : emp.getName());
        emp.setSalary(dto.getSalary() != null ? dto.getSalary() : emp.getSalary());
        emp.setDepartment(dto.getDepartment() != null ? dto.getDepartment() : emp.getDepartment());
        return toViewEmpDto(employeeRepository.save(emp));
    }


    //delete
    public void deleteEmp(Long id){
        employeeRepository.findById(id).orElseThrow(()-> new NoSuchElementException("User with this id does not exist"));
        employeeRepository.deleteById(id);
    }



    public ViewEmployeeDto toViewEmpDto(Employee employee){
        return ViewEmployeeDto.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .dateOfJoining(employee.getDateOfJoining().toString())
                .build();
    }

}
