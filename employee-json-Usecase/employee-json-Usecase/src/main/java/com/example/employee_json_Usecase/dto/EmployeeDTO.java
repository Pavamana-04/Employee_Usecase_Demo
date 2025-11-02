package com.example.employee_json_Usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private Double salary;
    private String position;
}