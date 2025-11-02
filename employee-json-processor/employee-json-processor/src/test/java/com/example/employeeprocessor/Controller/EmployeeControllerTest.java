package com.example.employeeprocessor.Controller;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testLoadEmployeesFromJson() throws Exception {

        Employee employee = new Employee(1, "Pavan", "Software Engineer", "IT", 15000.0, "Pavan@test.com");
        List<Employee> employees = Arrays.asList(employee);

        when(employeeService.loadEmployeesFromJson()).thenReturn(employees);


        mockMvc.perform(post("/api/employees/load-from-json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Pavan"))
                .andExpect(jsonPath("$[0].position").value("Software Engineer"));
    }

    @Test
    void testGetAllEmployees() throws Exception {

        Employee employee = new Employee(1, "Pavan", "Developer", "IT", 15000.0, "Pavan@test.com");
        List<Employee> employees = Arrays.asList(employee);

        when(employeeService.getAllEmployees()).thenReturn(employees);


        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Pavan"));
    }

    @Test
    void testGetEmployeeCount() throws Exception {

        when(employeeService.getEmployeeCount()).thenReturn(5L);


        mockMvc.perform(get("/api/employees/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}