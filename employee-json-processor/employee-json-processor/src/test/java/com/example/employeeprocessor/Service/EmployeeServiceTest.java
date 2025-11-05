package com.example.employeeprocessor.Service;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private Employee testEmployee;
    private List<Employee> testEmployees;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee(1, "Pavan", "Software Engineer", "IT", 15000.0, "Pavan.K@company.com");
        testEmployees = Arrays.asList(
                testEmployee,
                new Employee(2, "Pavamana", "Project Manager", "IT", 25000.0, "Pavamana@company.com")
        );
    }



    @Test
    void testLoadEmployeesFromJson_Success() throws Exception {

        List<Employee> employees = employeeService.loadEmployeesFromJson();


        assertNotNull(employees);

    }

    @Test
    void testGetAllEmployees_Success() {

        when(employeeRepository.findAll()).thenReturn(testEmployees);


        List<Employee> employees = employeeService.getAllEmployees();


        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals("Pavan", employees.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeCount_Success() {

        when(employeeRepository.count()).thenReturn(5L);


        long count = employeeService.getEmployeeCount();


        assertEquals(5L, count);
        verify(employeeRepository, times(1)).count();
    }

    @Test
    void testClearAllEmployees_Success() {

        doNothing().when(employeeRepository).deleteAll();


        employeeService.clearAllEmployees();


        verify(employeeRepository, times(1)).deleteAll();
    }

    @Test
    void testSaveEmployee_Success() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);


        Employee result = employeeService.saveEmployee(testEmployee);


        assertNotNull(result);
        assertEquals("Pavan", result.getName());
        assertEquals("Software Engineer", result.getPosition());
        verify(employeeRepository, times(1)).save(testEmployee);
    }



    @Test
    void testGetAllEmployees_EmptyList() {

        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());


        List<Employee> employees = employeeService.getAllEmployees();


        assertNotNull(employees);
        assertTrue(employees.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeCount_Zero() {

        when(employeeRepository.count()).thenReturn(0L);


        long count = employeeService.getEmployeeCount();


        assertEquals(0L, count);
        verify(employeeRepository, times(1)).count();
    }

    @Test
    void testSaveEmployee_NullEmployee() {

        when(employeeRepository.save( null)).thenThrow(new IllegalArgumentException());


        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(null));
    }

    @Test
    void testClearAllEmployees_DatabaseError() {

        doThrow(new RuntimeException("Database error")).when(employeeRepository).deleteAll();


        assertThrows(RuntimeException.class, () -> employeeService.clearAllEmployees());
    }

    @Test
    void testGetAllEmployees_DatabaseError() {

        when(employeeRepository.findAll()).thenThrow(new RuntimeException("Database error"));


        assertThrows(RuntimeException.class, () -> employeeService.getAllEmployees());
    }
}