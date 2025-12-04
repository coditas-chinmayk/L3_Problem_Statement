package com.example.L3.dto;

import com.example.L3.entity.Task;

import java.util.List;

public class ViewEmpTasksDto {
    private Long id;
    private String name;
    private String email;
    private List<Task> tasks;
}
