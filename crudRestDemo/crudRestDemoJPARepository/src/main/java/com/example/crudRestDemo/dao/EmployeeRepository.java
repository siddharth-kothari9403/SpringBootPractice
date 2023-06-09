package com.example.crudRestDemo.dao;

import com.example.crudRestDemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

//gives us all the default implementations, using generic class T which is now employee, and primary key type integer

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
