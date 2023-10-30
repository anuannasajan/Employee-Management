package com.example.employee.management.repository;

import com.example.employee.management.model.Employee;
import org.hibernate.boot.archive.internal.JarProtocolArchiveDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

  //  Object existbyemployee(String firstName, String lastName, String email, String position);

}
