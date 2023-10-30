package com.example.employee.management.exception;

public class NotFoundExceptionEmployee extends RuntimeException {
    public NotFoundExceptionEmployee(String Message){
        super(Message);
    }
}
