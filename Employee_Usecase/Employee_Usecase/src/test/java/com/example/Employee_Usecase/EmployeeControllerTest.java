package com.example.Employee_Usecase;

import com.example.Employee_Usecase.Controller.EmployeeController;
import com.example.Employee_Usecase.model.Employee;
import com.example.Employee_Usecase.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee1;
    private Employee employee2;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();

        employee1 = new Employee("John Doe", "Software Engineer");
        employee1.setId(1L);

        employee2 = new Employee("Jane Smith", "Product Manager");
        employee2.setId(2L);
    }

    @Test
    void testGetAllEmployees() throws Exception {
        // Arrange
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        // Act & Assert
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].employeeName").value("John Doe"))
                .andExpect(jsonPath("$[1].employeeName").value("Jane Smith"));
    }

    @Test
    void testGetEmployeeById_Found() throws Exception {
        // Arrange
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee1));

        // Act & Assert
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("John Doe"))
                .andExpect(jsonPath("$.employeePosition").value("Software Engineer"));
    }

    @Test
    void testGetEmployeeById_NotFound() throws Exception {
        // Arrange
        when(employeeService.getEmployeeById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/employees/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEmployee() throws Exception {
        // Arrange
        Employee newEmployee = new Employee("Bob Wilson", "QA Engineer");
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(newEmployee);

        // Act & Assert
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.employeeName").value("Bob Wilson"));
    }

    @Test
    void testUpdateEmployee_Success() throws Exception {
        // Arrange
        Employee updatedEmployee = new Employee("John Updated", "Senior Software Engineer");
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(updatedEmployee);

        // Act & Assert
        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("John Updated"));
    }

    @Test
    void testUpdateEmployee_NotFound() throws Exception {
        // Arrange
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/employees/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEmployee_Success() throws Exception {
        // Arrange
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteEmployee_NotFound() throws Exception {
        // Arrange
        when(employeeService.deleteEmployee(99L)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/employees/99"))
                .andExpect(status().isNotFound());
    }
}