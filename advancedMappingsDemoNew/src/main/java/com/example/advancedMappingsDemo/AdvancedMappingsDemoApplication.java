package com.example.advancedMappingsDemo;

import com.example.advancedMappingsDemo.dao.AppDao;
import com.example.advancedMappingsDemo.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AdvancedMappingsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedMappingsDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDao appDao){
		return runner -> {

//			createInstructor(appDao);
//			deleteInstructorById(appDao);
//			getInstructorById(appDao);
//			getInstructorDetailById(appDao);
//			deleteInstructorDetailById(appDao);
//			createInstructorWithCourses(appDao);
//			findInstructorWithCourses(appDao);
			findCoursesForInstructor(appDao);
		};
	}


	public void createInstructor(AppDao appDao){
		Instructor instructor = new Instructor("Chad", "Darby", "darby@luv2code.com");
		InstructorDetail instructorDetail = new InstructorDetail("http://www.youtube.com/luv2code", "Swimming");
		instructor.setInstructorDetail(instructorDetail);

		System.out.println("Saving instructor");
		appDao.save(instructor);

		System.out.println("Done");
	}


	public void getInstructorById(AppDao appDao){
		int id = 3;
		Instructor instructor = appDao.findInstructorById(id);
		System.out.println(instructor);
	}

	public void deleteInstructorById(AppDao appDao){
		int id = 4;
		System.out.println("Deleting instructor with id: "+id);
		appDao.deleteInstructorById(id);
		System.out.println("Successful");
	}

	public void getInstructorDetailById(AppDao appDao){
		try{
			int id = 3;
			InstructorDetail instructorDetail = appDao.getDetailById(id);
			System.out.println("Instructor details: "+instructorDetail);
			System.out.println("Instructor: "+instructorDetail.getInstructor());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void deleteInstructorDetailById(AppDao appDao){
		int id = 5;
		System.out.println("Deleting instructor with id: "+id);
		appDao.deleteInstructorDetailById(id);
		System.out.println("Successful");
	}

	public void createInstructorWithCourses(AppDao appDao){

		Instructor instructor = new Instructor("Chad", "Darby", "darby@luv2code.com");
		InstructorDetail instructorDetail = new InstructorDetail("http://www.youtube.com/luv2code", "Swimming");
		instructor.setInstructorDetail(instructorDetail);

		Course course1 = new Course("Spring Boot");
		Course course2 = new Course("MongoDB");

		instructor.addCourse(course1);
		instructor.addCourse(course2);

		appDao.save(instructor);
	}

	public void findInstructorWithCourses(AppDao appDao){
		int id = 1;
		Instructor instructor = appDao.findInstructorById(id);

		System.out.println(instructor);
//		System.out.println(instructor.getCourses());
	}

	public void findCoursesForInstructor(AppDao appDao){
		int id = 1;
		findInstructorWithCourses(appDao);

		System.out.println(appDao.findCoursesByInstructorId(id));
	}

	public void findInstructorByIdJoinFetch(AppDao appDao){
		int id = 1;
		Instructor instructor = appDao.findInstructorByIdJoinFetch(id);

		System.out.println(instructor);
		System.out.println(instructor.getCourses());
	}
}
