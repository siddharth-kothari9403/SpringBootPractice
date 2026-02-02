package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.RoleDAO;
import com.example.OrganManagementSystem.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtRolesService {
    @Autowired
    private RoleDAO roleDAO;

    public Role findByName(String name) {
        return roleDAO.findRoleByName(name);
    }
}
