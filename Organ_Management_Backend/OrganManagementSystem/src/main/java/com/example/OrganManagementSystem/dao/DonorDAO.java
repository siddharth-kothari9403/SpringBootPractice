package com.example.OrganManagementSystem.dao;

import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonorDAO extends CrudRepository<Donor, Integer> {
    @Query("SELECT d from Donor d where d.patientInformation.patientId = :id")
    List<Donor> getDonorByPatientId(@Param("id") Integer id);
}
