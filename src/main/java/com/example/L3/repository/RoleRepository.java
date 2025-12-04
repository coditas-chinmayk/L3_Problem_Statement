package com.example.L3.repository;

import com.example.L3.constant.EmployeeRole;
import com.example.L3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(EmployeeRole roleEnum);
}
