package com.example.employeeprocessor.Controller;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*") // ✅ ADD THIS LINE FOR CORS
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ✅ EXISTING ENDPOINTS - KEEP AS IS
    @PostMapping("/load-from-json")
    public ResponseEntity<List<Employee>> loadEmployeesFromJson() {
        try {
            List<Employee> employees = employeeService.loadEmployeesFromJson();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
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

    // ✅ ADD THESE NEW ENDPOINTS FOR FULL CRUD:

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            return employee.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}