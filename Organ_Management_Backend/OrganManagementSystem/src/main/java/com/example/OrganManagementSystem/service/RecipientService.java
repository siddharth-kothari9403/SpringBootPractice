package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.RecipientDAO;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipientService {
    private RecipientDAO recipientDAO;
    private PatientInfoDAO patientInfoDAO;

    public RecipientService(RecipientDAO recipientDAO, PatientInfoDAO patientInfoDAO) {
        this.patientInfoDAO = patientInfoDAO;
        this.recipientDAO = recipientDAO;
    }

    public List<Recipient> getRecipientByPatientId(Integer id) throws PatientNotFoundException{
        Optional<PatientInformation> patientInformation = this.patientInfoDAO.findById(id);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        return recipientDAO.getRecipientByPatientId(id);
    }

    public Recipient addInfo(Recipient recipient){
        return recipientDAO.save(recipient);
    }

    public Recipient updateInfo(Recipient recipient){
        return recipientDAO.save(recipient);
    }

    public Optional<Recipient> viewInfoById(Integer id) throws RecipientNotFoundException {
        Optional<Recipient> recipient = this.recipientDAO.findById(id);
        if (recipient.isEmpty()){
            throw new RecipientNotFoundException();
        }
        return recipient;
    }

    public List<Recipient> getAllRecipients(){
        return (List<Recipient>)recipientDAO.findAll();
    }
}
