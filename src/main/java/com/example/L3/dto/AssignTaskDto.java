package com.example.L3.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AssignTaskDto {
    private String taskId;
    private String empId;
}
