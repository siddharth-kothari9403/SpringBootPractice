package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DoctorInfoDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.exception.DoctorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.example.OrganManagementSystem.entity.PatientInformation;

@Service
public class AdminService {

    private PatientInfoDAO patientInfoDAO;
    private DoctorInfoDAO doctorInfoDAO;


    @Autowired
    public AdminService(PatientInfoDAO patientInfoDAO, DoctorInfoDAO doctorInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
        this.doctorInfoDAO = doctorInfoDAO;
    }

    public List<PatientInformation> showPatients() {
        return (List<PatientInformation>) patientInfoDAO.findAll();
    }

    public List<DoctorInformation> showDoctors() {
        return (List<DoctorInformation>) doctorInfoDAO.findAll();
    }

    public Optional<PatientInformation> showPatientById(Integer id) throws PatientNotFoundException {
        Optional<PatientInformation> patientInformation = this.patientInfoDAO.findById(id);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        return patientInformation;
    }

    public Optional<DoctorInformation> showDoctorById(Integer id) {
        Optional<DoctorInformation> theDoctorInformation = this.doctorInfoDAO.findById(id);

        if (theDoctorInformation.isEmpty()) {
            throw new DoctorNotFoundException();
        }
        return theDoctorInformation;
    }
}

