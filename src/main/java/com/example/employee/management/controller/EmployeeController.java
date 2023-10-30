package com.example.employee.management.controller;

import com.example.employee.management.contract.EmployeeRequest;
import com.example.employee.management.contract.EmployeeResponse;
import com.example.employee.management.service.EmployeeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
   public ResponseEntity<EmployeeResponse> addEmployee( @RequestBody EmployeeRequest employee) {
      EmployeeResponse newEmployee = employeeService.addEmployee(employee);
       return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
   }
    @GetMapping

    public ResponseEntity< List<EmployeeResponse>> getAllemployees() {
        List<EmployeeResponse> response = employeeService.getAllEmployees();
        return ResponseEntity.ok(response);


    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable int id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-by/{employeeId}")
    public ResponseEntity<EmployeeResponse> updateEmployeeById(@PathVariable int employeeId, @RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse response = employeeService.updateEmployeeById(employeeId, employeeRequest);
        return ResponseEntity.ok(response);


    }
    @DeleteMapping("/delete-by/{employeeId}")
    public ResponseEntity<String> deleteAnEmployeeById(@PathVariable int employeeId){
        String response = employeeService.deleteAnEmployeeById(employeeId);
        return ResponseEntity.ok(response);


    }
}



