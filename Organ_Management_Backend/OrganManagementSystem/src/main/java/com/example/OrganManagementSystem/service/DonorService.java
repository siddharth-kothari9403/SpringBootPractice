package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DonorDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DonorService {
    private DonorDAO donorDAO;

    private PatientInfoDAO patientInfoDAO;

    @Autowired
    public DonorService(DonorDAO donorDAO, PatientInfoDAO patientInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
        this.donorDAO = donorDAO;
    }

    public List<Donor> getDonorByPatientId(Integer id) throws PatientNotFoundException {
        Optional<PatientInformation> patientInformation = this.patientInfoDAO.findById(id);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        return donorDAO.getDonorByPatientId(id);
    }

    public Donor addInfo(Donor donor){
        return donorDAO.save(donor);
    }

    public Optional<Donor> viewInfoById(Integer id) throws DonorNotFoundException {
        Optional<Donor> donor = this.donorDAO.findById(id);
        if (donor.isEmpty()){
            throw new DonorNotFoundException();
        }
        return donor;
    }

    public List<Donor> getAllDonors(){
        return (List<Donor>)donorDAO.findAll();
    }
}
