package com.example.employee.management.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest{

        private String firstName;
        private String lastName;
        private String email;

        private String position;



}



