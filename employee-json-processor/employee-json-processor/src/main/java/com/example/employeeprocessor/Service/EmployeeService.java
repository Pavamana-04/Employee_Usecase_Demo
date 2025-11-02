package com.example.employeeprocessor.Service;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Employee> loadEmployeesFromJson() throws Exception {
        ClassPathResource resource = new ClassPathResource("employees.json");
        InputStream inputStream = resource.getInputStream();

        List<Employee> employees = objectMapper.readValue(inputStream, new TypeReference<List<Employee>>() {});


        return employeeRepository.saveAll(employees);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public long getEmployeeCount() {
        return employeeRepository.count();
    }

    public void clearAllEmployees() {
        employeeRepository.deleteAll();
    }
}