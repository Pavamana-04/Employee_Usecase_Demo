package com.example.Employee_Usecase;

import com.example.Employee_Usecase.model.Employee;
import com.example.Employee_Usecase.repository.EmployeeRepository;
import com.example.Employee_Usecase.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        employee1 = new Employee("John Doe", "Software Engineer");
        employee1.setId(1L);

        employee2 = new Employee("Jane Smith", "Product Manager");
        employee2.setId(2L);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_Found() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        // Act
        Optional<Employee> result = employeeService.getEmployeeById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getEmployeeName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Employee> result = employeeService.getEmployeeById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(employeeRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateEmployee() {
        // Arrange
        Employee newEmployee = new Employee("Bob Wilson", "QA Engineer");
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

        // Act
        Employee result = employeeService.createEmployee(newEmployee);

        // Assert
        assertNotNull(result);
        assertEquals("Bob Wilson", result.getEmployeeName());
        assertEquals("QA Engineer", result.getEmployeePosition());
        verify(employeeRepository, times(1)).save(newEmployee);
    }

    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        Employee updatedDetails = new Employee("John Updated", "Senior Software Engineer");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedDetails);

        // Act
        Employee result = employeeService.updateEmployee(1L, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getEmployeeName());
        assertEquals("Senior Software Engineer", result.getEmployeePosition());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        Employee updatedDetails = new Employee("John Updated", "Senior Software Engineer");
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Employee result = employeeService.updateEmployee(99L, updatedDetails);

        // Assert
        assertNull(result);
        verify(employeeRepository, times(1)).findById(99L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_Success() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = employeeService.deleteEmployee(1L);

        // Assert
        assertTrue(result);
        verify(employeeRepository, times(1)).existsById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        // Arrange
        when(employeeRepository.existsById(99L)).thenReturn(false);

        // Act
        boolean result = employeeService.deleteEmployee(99L);

        // Assert
        assertFalse(result);
        verify(employeeRepository, times(1)).existsById(99L);
        verify(employeeRepository, never()).deleteById(99L);
    }
}
