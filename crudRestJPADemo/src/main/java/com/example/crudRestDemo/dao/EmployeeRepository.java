package com.example.crudRestDemo.dao;

import com.example.crudRestDemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//gives us all the default implementations, using generic class T which is now employee, and primary key type integer

//To change the endpoint
//@RepositoryRestResource(path="members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

//for sorting and configuration, see last lecture of section 4 of the course
