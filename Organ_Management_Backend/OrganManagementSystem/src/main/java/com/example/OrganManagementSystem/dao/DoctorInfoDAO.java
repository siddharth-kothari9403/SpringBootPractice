package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.DoctorInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorInfoDAO extends CrudRepository<DoctorInformation, Integer> {
    @Query("SELECT dc from DoctorInformation dc where dc.user.id = :id")
    DoctorInformation getDoctorByUserId(@Param("id") Integer id);
}
