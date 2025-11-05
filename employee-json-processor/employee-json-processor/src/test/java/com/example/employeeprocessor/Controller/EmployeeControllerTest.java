package com.example.employeeprocessor.Controller;

import com.example.employeeprocessor.model.Employee;
import com.example.employeeprocessor.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

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

        when(employeeService.loadEmployeesFromJson()).thenReturn(testEmployees);
        mockMvc.perform(post("/api/employees/load-from-json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Pavan"))
                .andExpect(jsonPath("$[0].position").value("Software Engineer"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Pavamana"));
    }

    @Test
    void testGetAllEmployees_Success() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(testEmployees);


        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pavan"))
                .andExpect(jsonPath("$[1].name").value("Pavamana"));
    }

    @Test
    void testGetEmployeeCount_Success() throws Exception {

        when(employeeService.getEmployeeCount()).thenReturn(5L);

        mockMvc.perform(get("/api/employees/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testClearEmployees_Success() throws Exception {

        doNothing().when(employeeService).clearAllEmployees();


        mockMvc.perform(delete("/api/employees"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).clearAllEmployees();
    }

    @Test
    void testCreateEmployee_Success() throws Exception {

        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(testEmployee);


        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Pavan"))
                .andExpect(jsonPath("$.position").value("Software Engineer"));
    }

    @Test
    void testGetAllEmployees_EmptyList() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());


        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetEmployeeCount_Zero() throws Exception {

        when(employeeService.getEmployeeCount()).thenReturn(0L);


        mockMvc.perform(get("/api/employees/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }


    @Test
    void testLoadEmployeesFromJson_ServiceException() throws Exception {

        when(employeeService.loadEmployeesFromJson()).thenThrow(new RuntimeException("JSON parsing error"));


        mockMvc.perform(post("/api/employees/load-from-json"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateEmployee_ServiceException() throws Exception {

        when(employeeService.saveEmployee(any(Employee.class))).thenThrow(new RuntimeException("Database error"));


        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testInvalidEndpoint() throws Exception {

        mockMvc.perform(get("/api/employees/invalid-endpoint"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEmployee_InvalidJSON() throws Exception {

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateEmployee_MissingContentType() throws Exception {

        mockMvc.perform(post("/api/employees")
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void testCreateEmployee_EmptyBody() throws Exception {

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllEmployees_NullReturn() throws Exception {

        when(employeeService.getAllEmployees()).thenReturn(null);


        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk());
    }



    @Test
    void testCreateEmployee_InvalidEmployeeData() throws Exception {

        Employee invalidEmployee = new Employee(null, "", "", "", -1000.0, "invalid-email");
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(invalidEmployee);


        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployeeCount_NegativeScenario() throws Exception {

        when(employeeService.getEmployeeCount()).thenReturn(1L);

        mockMvc.perform(get("/api/employees/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}