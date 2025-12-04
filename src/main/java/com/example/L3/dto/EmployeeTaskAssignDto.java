package com.example.L3.dto;

import com.example.L3.entity.Task;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTaskAssignDto {
    private Long id;
    private String name;
    private List<Task> tasks;
}
