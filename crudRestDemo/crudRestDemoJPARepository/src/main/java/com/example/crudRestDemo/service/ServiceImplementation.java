package com.example.crudRestDemo.service;

import com.example.crudRestDemo.dao.EmployeeRepository;
import com.example.crudRestDemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//service layer is used to delegate the calls to the required dao, instead of the rest controller handling the daos
@Service
public class ServiceImplementation implements EmployeeService{

    private EmployeeRepository employeeRepository;
    @Autowired
    public ServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> res = employeeRepository.findById(id);

        Employee employee = null;
        if (res.isPresent()){
            employee = res.get();
        }else{
            throw new RuntimeException("Did not find employee with id - "+id);
        }

        return employee;
    }

    //no need for transactional

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}
