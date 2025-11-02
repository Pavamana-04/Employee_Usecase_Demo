package com.example.employeeprocessor.runner;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading employees from JSON file...");
        List<Employee> employees = employeeService.loadEmployeesFromJson();
        System.out.println("Loaded " + employees.size() + " employees into database.");

        // Display loaded employees
        employees.forEach(employee ->
                System.out.printf("ID: %d, Name: %s, Position: %s%n",
                        employee.getId(), employee.getName(), employee.getPosition()));
    }
}