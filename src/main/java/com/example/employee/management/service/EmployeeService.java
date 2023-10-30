package com.example.employee.management.service;

import com.example.employee.management.contract.EmployeeRequest;
import com.example.employee.management.contract.EmployeeResponse;
import com.example.employee.management.exception.NotFoundExceptionEmployee;
import com.example.employee.management.model.Employee;
import com.example.employee.management.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .position(employeeRequest.getPosition())
                .build();

        Employee saved = employeeRepository.save(employee);
        return EmployeeResponse.builder()
                .id(saved.getId())
                .firstName(saved.getFirstName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .position(saved.getPosition())
                .build();
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        // Use ModelMapper to convert entities to DTOs
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.EmployeeResponseBuilder.class).build())
                .collect(Collectors.toList());
    }
    public EmployeeResponse getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Use ModelMapper to map the Employee entity to the EmployeeResponse DTO using the builder
        EmployeeResponse response = modelMapper.map(employee, EmployeeResponse.EmployeeResponseBuilder.class)
                .id(employee.getId())
                .build();

        return response;

    }
    public EmployeeResponse updateEmployeeById(long employeeId, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new NotFoundExceptionEmployee("Employee not found");
        }
        employee = Employee.
                builder()
                .id(employee.getId())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .email(employeeRequest.getEmail())
                .position(employeeRequest.getPosition())
                .build();
        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeResponse.
                builder()
                .id(updatedEmployee.getId())
                .firstName(updatedEmployee.getFirstName())
                .lastName(updatedEmployee.getLastName())
                .email(updatedEmployee.getEmail())
                .position(updatedEmployee.getPosition())
                .build();
    }
    public String deleteAnEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new NotFoundExceptionEmployee("Employee not found");
        }
        employeeRepository.delete(employee);
        if (employeeRepository.existsById(employeeId)) {
            throw new NotFoundExceptionEmployee("Failed to delete the employee with ID " + employeeId);
        } else {
            return "Successfully deleted the employee with ID " + employeeId;
        }
    }
}



