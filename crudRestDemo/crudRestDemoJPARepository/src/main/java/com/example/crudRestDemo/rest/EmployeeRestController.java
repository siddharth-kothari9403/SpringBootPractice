package com.example.crudRestDemo.rest;

import com.example.crudRestDemo.entity.Employee;
import com.example.crudRestDemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return this.employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId){
        Employee employee = this.employeeService.findById(employeeId);

        if (employee == null){
            throw new RuntimeException("Employee id not found - "+employeeId);
        }

        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
        //requestBody to tell that it comes from request body, which is converted by Jackson
        employee.setId(0);
        return this.employeeService.save(employee);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        return this.employeeService.save(employee);
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Employee employee = this.employeeService.findById(employeeId);

        if (employee == null){
            throw new RuntimeException("Employee id not found - "+employeeId);
        }

        employeeService.deleteById(employeeId);
        return "Deleted Employee Id - "+employeeId;
    }

}
