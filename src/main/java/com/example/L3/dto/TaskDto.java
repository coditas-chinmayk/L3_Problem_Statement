package com.example.L3.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class TaskDto {
    private String id;
    private String description;
    private String status;
    private LocalDate deadline;
    private String employeeId;
}
