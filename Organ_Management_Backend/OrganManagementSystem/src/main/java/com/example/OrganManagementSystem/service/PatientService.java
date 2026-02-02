package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class PatientService {
    private PatientInfoDAO patientInfoDAO;

    @Autowired
    public PatientService(PatientInfoDAO patientInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
    }

    @Transactional
    public PatientInformation updatePatientInfo(PatientInformation patientInformation) {
        return patientInfoDAO.save(patientInformation);
    }

    @Transactional
    public PatientInformation addPatientInfo(PatientInformation patientInformation) {
        return patientInfoDAO.save(patientInformation);
    }

    public PatientInformation viewPatientByUserId(Integer id) throws PatientNotFoundException{
        PatientInformation patientInformation  = this.patientInfoDAO.getPatientByUserId(id);
        if (patientInformation == null){
            throw new PatientNotFoundException();
        }
        return patientInformation;
    }

    public Optional<PatientInformation> viewPatientInfo(Integer id) throws PatientNotFoundException {
        Optional<PatientInformation> patientInformation = this.patientInfoDAO.findById(id);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        return patientInformation;
    }
}

