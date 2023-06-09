package com.example.thymeleafCrudDemo.controller;

import com.example.thymeleafCrudDemo.entity.Employee;
import com.example.thymeleafCrudDemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	// load employee data

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService){
		this.employeeService = employeeService;
	}

//	private List<Employee> theEmployees;

//	@PostConstruct
//	private void loadData() {
//
//		// create employees
//		Employee emp1 = new Employee("Leslie", "Andrews", "leslie@luv2code.com");
//		Employee emp2 = new Employee("Emma", "Baumgarten", "emma@luv2code.com");
//		Employee emp3 = new Employee("Avani", "Gupta", "avani@luv2code.com");
//
//		// create the list
//		theEmployees = new ArrayList<>();
//
//		// add to the list
//		theEmployees.add(emp1);
//		theEmployees.add(emp2);
//		theEmployees.add(emp3);
//	}

	// add mapping for "/list"

	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		List<Employee> employees = employeeService.findAll();

		// add to the spring model
		theModel.addAttribute("employees", employees);

		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model){
		Employee employee = new Employee();
		model.addAttribute("employee", employee);

		return "employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int id, Model model){
		//receives id from the parameter in the url, and opens the form with it
		Employee employee = employeeService.findById(id);
		model.addAttribute("employee", employee);

		return "employees/employee-form";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int id){
		employeeService.deleteById(id);
		return "redirect:/employees/list";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Employee employee){
		//the model attribute is taken here, which had been updated by the setter when button was pressed
		employeeService.save(employee);
		return "redirect:/employees/list";
	}
}









