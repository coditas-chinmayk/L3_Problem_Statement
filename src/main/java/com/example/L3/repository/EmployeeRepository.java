package com.example.L3.repository;

import com.example.L3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);

    Optional<Employee> findByEmail(String email);
}
