package com.example.employee.management.controller;
import com.example.employee.management.contract.EmployeeRequest;
import com.example.employee.management.contract.EmployeeResponse;
import com.example.employee.management.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testAddEmployee() throws Exception {
        EmployeeRequest request = new EmployeeRequest("John", "Doe", "john.doe@example.com", "Manager");
        EmployeeResponse response = new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com", "Manager");

        Mockito.when(employeeService.addEmployee(Mockito.any(EmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"position\":\"Manager\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"position\":\"Manager\"}"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<EmployeeResponse> employees = Collections.singletonList(new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com", "Manager"));

        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"position\":\"Manager\"}]"));
    }
        @Test

        public void testGetEmployeeById() throws Exception {
            EmployeeResponse response = new EmployeeResponse(1L, "John", "Doe", "john.doe@example.com", "Manager");

            Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.get("/employee/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"position\":\"Manager\"}"));
        }
        @Test
        public void testUpdateEmployeeById() throws Exception {
            EmployeeRequest request = new EmployeeRequest("Jane", "Smith", "jane.smith@example.com", "Supervisor");
            EmployeeResponse response = new EmployeeResponse(1L, "Jane", "Smith", "jane.smith@example.com", "Supervisor");

            Mockito.when(employeeService.updateEmployeeById(1L, request)).thenReturn(response);

            mockMvc.perform(MockMvcRequestBuilders.put("/employee/update-by/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"email\":\"jane.smith@example.com\",\"position\":\"Supervisor\"}"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"email\":\"jane.smith@example.com\",\"position\":\"Supervisor\"}"));
        }
        @Test
        public void testDeleteAnEmployeeById() throws Exception {
            Mockito.when(employeeService.deleteAnEmployeeById(1L)).thenReturn("Successfully deleted the employee with ID 1");

            mockMvc.perform(MockMvcRequestBuilders.delete("/employee/delete-by/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("Successfully deleted the employee with ID 1"));
        }
    }




