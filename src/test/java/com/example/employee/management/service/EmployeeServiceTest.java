package com.example.employee.management.service;

import com.example.employee.management.contract.EmployeeRequest;
import com.example.employee.management.contract.EmployeeResponse;
import com.example.employee.management.model.Employee;
import com.example.employee.management.repository.EmployeeRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    private EmployeeService employeeService;



    @BeforeEach

    public void init() {

        MockitoAnnotations.openMocks(this);

        employeeRepository = Mockito.mock(EmployeeRepository.class);

        modelMapper = new ModelMapper();

        employeeService = new EmployeeService(employeeRepository, modelMapper);
    }
    @Test

    void testAddEmployee(){

        EmployeeRequest request = new EmployeeRequest("Anu","sajan","wdfgh@email.com","1");


        Employee employee = modelMapper.map(request, Employee.class);

        EmployeeResponse expectedResponse = modelMapper.map(employee, EmployeeResponse.class);



        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeResponse actualResponse = employeeService.addEmployee(request);

        assertEquals(expectedResponse, actualResponse);

    }
    @Test
    void testUpdateEmployeeById() {

        long employeeId = 1L;
        EmployeeRequest request = new EmployeeRequest("Test First Name", "Test Last Name", "test.email@example.com", "Test Position");

        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .position(request.getPosition())
                .build();

        EmployeeResponse expectedResponse = modelMapper.map(employee, EmployeeResponse.class);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        when(employeeRepository.save(any())).thenReturn(employee);

        EmployeeResponse actualResponse = employeeService.updateEmployeeById(employeeId, request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testDeleteAnEmployeeById() {

        long employeeId = 1L;

        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .email("test.email@example.com")
                .position("Test Position")
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        doNothing().when(employeeRepository).delete(employee);

        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        String expectedResponse = "Successfully deleted the employee with ID " + employeeId;

        String actualResponse = employeeService.deleteAnEmployeeById(employeeId);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testGetEmployeeById() {

        long employeeId = 1L;

        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .email("test.email@example.com")
                .position("Test Position")
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        EmployeeResponse expectedResponse = modelMapper.map(employee, EmployeeResponse.EmployeeResponseBuilder.class)
                .id(employee.getId())
                .build();

        EmployeeResponse actualResponse = employeeService.getEmployeeById(employeeId);

        assertEquals(expectedResponse, actualResponse);
    }
    @Test
    void testGetAllEmployees() {

        List<Employee> employees = Arrays.asList(
                Employee.builder()
                        .id(1L)
                        .firstName("Test First Name 1")
                        .lastName("Test Last Name 1")
                        .email("test1.email@example.com")
                        .position("Test Position 1")
                        .build(),
                Employee.builder()
                        .id(2L)
                        .firstName("Test First Name 2")
                        .lastName("Test Last Name 2")
                        .email("test2.email@example.com")
                        .position("Test Position 2")
                        .build()
        );

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeResponse> expectedResponses = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.EmployeeResponseBuilder.class).build())
                .collect(Collectors.toList());

        List<EmployeeResponse> actualResponses = employeeService.getAllEmployees();

        assertEquals(expectedResponses, actualResponses);
    }




}
