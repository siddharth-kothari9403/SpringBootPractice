package com.example.advancedMappingsDemo.dao;

import com.example.advancedMappingsDemo.entity.Course;
import com.example.advancedMappingsDemo.entity.Instructor;
import com.example.advancedMappingsDemo.entity.InstructorDetail;

import java.util.List;

public interface AppDao {
    void save(Instructor instructor);

    Instructor findInstructorById(int id);

    void deleteInstructorById(int id);

    InstructorDetail getDetailById(int id);

    void deleteInstructorDetailById(int id);

    List<Course> findCoursesByInstructorId(int id);

    Instructor findInstructorByIdJoinFetch(int id);

}
