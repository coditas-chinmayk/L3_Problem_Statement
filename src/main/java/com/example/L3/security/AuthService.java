package com.example.L3.security;

import com.example.L3.dto.ApiResponseDto;
import com.example.L3.dto.LoginResponseDto;
import com.example.L3.entity.Employee;
import com.example.L3.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeeRepository employeeRepository;

    public LoginResponseDto login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        Employee user = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoginResponseDto(user.getName(), user.getEmail(), jwtUtil.generateToken(email));
    }
}