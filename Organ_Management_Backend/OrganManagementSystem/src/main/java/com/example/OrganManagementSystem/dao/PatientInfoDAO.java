package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.PatientInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientInfoDAO extends CrudRepository<PatientInformation, Integer> {
    @Query("SELECT pt from PatientInformation pt where pt.user.id = :id")
    PatientInformation getPatientByUserId(@Param("id") Integer id);
}