package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DoctorInfoDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.DoctorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService {

    private PatientInfoDAO patientInfoDAO;
    private DoctorInfoDAO doctorInfoDAO;

    @Autowired
    public DoctorService(PatientInfoDAO patientInfoDAO, DoctorInfoDAO doctorInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
        this.doctorInfoDAO = doctorInfoDAO;
    }

    public DoctorInformation getDoctorByUserId(Integer id) throws DoctorNotFoundException{
        DoctorInformation doctorInformation = doctorInfoDAO.getDoctorByUserId(id);
        if (doctorInformation == null){
            throw new DoctorNotFoundException();
        }
        return doctorInformation;
    }

    public DoctorInformation addDocInfo(DoctorInformation doctorInformation) {
        return doctorInfoDAO.save(doctorInformation);
    }

    public List<PatientInformation> showPatients() {
        return (List<PatientInformation>) patientInfoDAO.findAll();
    }

    public Optional<PatientInformation> showPatientById(Integer id) throws PatientNotFoundException {
        Optional<PatientInformation> patientInformation = this.patientInfoDAO.findById(id);
        if (patientInformation.isEmpty()) {
            throw new PatientNotFoundException();
        }
        return patientInformation;
    }

    public DoctorInformation updateMyInfo(DoctorInformation doctorInformation){
        return doctorInfoDAO.save(doctorInformation);
    }

}

