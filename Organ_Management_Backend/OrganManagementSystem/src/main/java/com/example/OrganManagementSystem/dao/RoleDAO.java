package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends CrudRepository<Role, Integer> {
    Role findRoleByName(String name);
}

