package com.example.crudRestDemo.service;

import com.example.crudRestDemo.dao.EmployeeDAO;
import com.example.crudRestDemo.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//service layer is used to delegate the calls to the required dao, instead of the rest controller handling the daos
@Service
public class ServiceImplementation implements EmployeeService{

    private EmployeeDAO employeeDAO;
    @Autowired
    public ServiceImplementation(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeDAO.findById(id);
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        employeeDAO.deleteById(id);
    }
}
