package com.example.restDemo.rest;

import com.example.restDemo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    @PostConstruct
    public void loadData(){
        this.students = new ArrayList<Student>();

        students.add(new Student("Poornima", "Patel"));
        students.add(new Student("Mark", "Doe"));
        students.add(new Student("Jack", "Son"));
    }

    //the rest controller makes sure to convert this list of students into a json array
    @GetMapping("/students")
    public List<Student> getStudents(){
        return this.students;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentByiD(@PathVariable int studentId){
        //PathVariable annotation tells us to take the path variable from the endpoint as an argument

        if (studentId >= this.students.size() || studentId < 0){
            throw new StudentNotFoundException("Student ID not found - "+ studentId);
        }

        return this.students.get(studentId);
    }

}
