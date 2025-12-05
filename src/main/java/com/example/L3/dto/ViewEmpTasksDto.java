package com.example.L3.dto;

import com.example.L3.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewEmpTasksDto {
    private Long id;
    private String name;
    private String email;
    private List<Task> tasks;
}
