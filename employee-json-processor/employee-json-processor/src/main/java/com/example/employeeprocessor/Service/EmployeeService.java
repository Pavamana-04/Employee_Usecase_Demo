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
import java.util.Optional;

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

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    public Optional<Employee> getEmployeeById(Integer id) {
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(Integer id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));


        employee.setName(employeeDetails.getName());
        employee.setPosition(employeeDetails.getPosition());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        employee.setEmail(employeeDetails.getEmail());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }


    public boolean employeeExists(Integer id) {
        return employeeRepository.existsById(id);
    }
}