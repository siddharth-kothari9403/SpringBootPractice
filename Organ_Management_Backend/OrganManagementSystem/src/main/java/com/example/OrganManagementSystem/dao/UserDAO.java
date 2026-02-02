package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
