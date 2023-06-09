package com.example.cruddemo.dao;

import com.example.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{

    //@Repository includes the @Component annotation, so this works

    //define entity manager
    private EntityManager entityManager;

    //inject the entity manager into the class (constructor injection)
    @Autowired
    public StudentDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Student student) {
        //persist method is used to add it to the database
        entityManager.persist(student);
    }

    @Override
    public Student findById(Integer id) {
        //the table names come under Student.class argument
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAllStudents(){
        //sql query in student
        //in typed query, use only the entity class name and fields, not the database table name and colummn names
        TypedQuery<Student> query = entityManager.createQuery("from Student order by lastName", Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        TypedQuery<Student> query = entityManager.createQuery("from Student where lastName=:name", Student.class);
        //setting parameters of the query similar to jdbc
        query.setParameter("name", lastName);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Student student = findById(id);
        entityManager.remove(student);
    }

    @Override
    @Transactional
    public Integer deleteAll() {
        return entityManager.createQuery("DELETE FROM Student").executeUpdate();
    }


}
