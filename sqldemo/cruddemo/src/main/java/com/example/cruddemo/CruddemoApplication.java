package com.example.cruddemo;

import com.example.cruddemo.dao.StudentDAO;
import com.example.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	//this is a command line application, this function opens the command line
	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO){
		return runner -> {
//			createStudent(studentDAO);
//			findStudentById(studentDAO);
//			getAllStudents(studentDAO);
//			getByLastName(studentDAO, "Doe");

//			updateStudentFirstName(studentDAO, "Mark");
//			findStudentById(studentDAO);

//			deleteStudentById(studentDAO, 2);
			deleteAll(studentDAO);
			getAllStudents(studentDAO);
		};

	}

	private void createStudent(StudentDAO studentDAO){
		System.out.println("Creating new Student");
		Student stud = new Student("Derek", "Adams", "derek@gmail.com");

		studentDAO.save(stud);

		System.out.println("Saved student with id: ");
		System.out.println(stud.getId());
	}

	private void findStudentById(StudentDAO studentDAO){
		System.out.println("Retrieving student with id 1");
		Student stud = studentDAO.findById(1);
		System.out.println(stud.toString());
	}

	private void getAllStudents(StudentDAO studentDAO){
		System.out.println("Retrieving Students...");
		List<Student> students = studentDAO.findAllStudents();

		for (Student curr : students){
			System.out.println(curr);
		}
	}

	private void getByLastName(StudentDAO studentDAO, String lastName){
		System.out.println("Retrieving students...");
		List<Student> students = studentDAO.findByLastName(lastName);

		for (Student curr : students){
			System.out.println(curr);
		}

	}

	private void updateStudentFirstName(StudentDAO studentDAO, String firstName){
		int studentID = 1;
		System.out.println("Updating student");
		Student student = studentDAO.findById(studentID);

		student.setFirstName(firstName);
		studentDAO.update(student);
		System.out.println("Updated successfully");
	}

	private void deleteStudentById(StudentDAO studentDAO, Integer id){
		System.out.println("Deleting record");
		studentDAO.delete(id);
	}

	private void deleteAll(StudentDAO studentDAO){
		System.out.println("Clearing records");
		studentDAO.deleteAll();
	}
}
