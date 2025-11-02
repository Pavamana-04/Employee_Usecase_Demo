package com.example.employeeprocessor.Controller;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/load-from-json")
    public ResponseEntity<List<Employee>> loadEmployeesFromJson() {
        try {
            List<Employee> employees = employeeService.loadEmployeesFromJson();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getEmployeeCount() {
        long count = employeeService.getEmployeeCount();
        return ResponseEntity.ok(count);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearEmployees() {
        employeeService.clearAllEmployees();
        return ResponseEntity.ok().build();
    }
}