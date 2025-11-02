package com.example.employeeprocessor.Service;

import com.example.employeeprocessor.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testLoadEmployeesFromJson() throws Exception {

        List<Employee> employees = employeeService.loadEmployeesFromJson();


        assertNotNull(employees);
        assertEquals(4, employees.size());


        Employee firstEmployee = employees.get(0);
        assertEquals(1, firstEmployee.getId());
        assertEquals("Pavan", firstEmployee.getName());
        assertEquals("Software Engineer", firstEmployee.getPosition());
        assertEquals("IT", firstEmployee.getDepartment());
        assertEquals(15000.0, firstEmployee.getSalary());
        assertEquals("Pavan.K@company.com", firstEmployee.getEmail());


        Employee secondEmployee = employees.get(1);
        assertEquals(2, secondEmployee.getId());
        assertEquals("Pavamana", secondEmployee.getName());
        assertEquals("Project Manager", secondEmployee.getPosition());
        assertEquals("IT", secondEmployee.getDepartment());
        assertEquals(25000.0, secondEmployee.getSalary());
        assertEquals("Pavamana@company.com", secondEmployee.getEmail());
    }

    @Test
    void testGetAllEmployees() {

        List<Employee> employees = employeeService.getAllEmployees();


        assertNotNull(employees);

    }

    @Test
    void testGetEmployeeCount() {

        Long count = employeeService.getEmployeeCount();


        assertNotNull(count);

    }
}