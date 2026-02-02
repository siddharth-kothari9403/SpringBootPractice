package com.example.OrganManagementSystem.service;

import com.example.OrganManagementSystem.dao.DonorDAO;
import com.example.OrganManagementSystem.dao.DonorRecipientMatchDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.RecipientDAO;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.MatchNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MatchService {
    private DonorRecipientMatchDAO donorRecipientMatchDAO;
    private DonorDAO donorDAO;

    private RecipientDAO recipientDAO;
    private PatientInfoDAO patientInformationDAO;

    @Autowired
    public MatchService(DonorRecipientMatchDAO donorRecipientMatchDAO, DonorDAO donorDAO, RecipientDAO recipientDAO, PatientInfoDAO patientInformationDAO){
        this.donorRecipientMatchDAO = donorRecipientMatchDAO;
        this.donorDAO = donorDAO;
        this.recipientDAO = recipientDAO;
        this.patientInformationDAO = patientInformationDAO;
    }

    public DonorRecipientMatch getMatchByDonorId(Integer id) throws DonorNotFoundException{
        Optional<Donor> donor = this.donorDAO.findById(id);
        if (donor.isEmpty()){
            throw new DonorNotFoundException();
        }
        return this.donorRecipientMatchDAO.getMatchByDonorId(id);
    }

    public DonorRecipientMatch getMatchByRecipientId(Integer id) throws RecipientNotFoundException {
        Optional< Recipient> recipient = this.recipientDAO.findById(id);
        if (recipient.isEmpty()){
            throw new RecipientNotFoundException();
        }
        return this.donorRecipientMatchDAO.getMatchByRecipientId(id);
    }

    public List<DonorRecipientMatch> getMatchesByDonorPatientId(Integer id) throws PatientNotFoundException {
        Optional<PatientInformation> patientInformation = this.patientInformationDAO.findById(id);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        return this.donorRecipientMatchDAO.getMatchesByDonorPatientId(id);
    }

    public List<DonorRecipientMatch> getMatchesByRecipientPatientId(Integer id) throws PatientNotFoundException {
        Optional<PatientInformation> patientInformation = this.patientInformationDAO.findById(id);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        return this.donorRecipientMatchDAO.getMatchesByRecipientPatientId(id);
    }

    public DonorRecipientMatch addMatch(DonorRecipientMatch donorRecipientMatch) throws DonorNotFoundException, RecipientNotFoundException {
        Optional<Donor> donor = this.donorDAO.findById(donorRecipientMatch.getDonor().getDonorId());
        if (donor.isEmpty()){
            throw new DonorNotFoundException();
        }

        Optional<Recipient> recipient = this.recipientDAO.findById(donorRecipientMatch.getRecipient().getRecipientId());
        if (recipient.isEmpty()){
            throw new RecipientNotFoundException();
        }

        return donorRecipientMatchDAO.save(donorRecipientMatch);
    }

    private Boolean isMatch(Donor donor, Recipient recipient){
        return donor.getPatientInformation().getBloodType().equals(recipient.getPatientInformation().getBloodType()) && donor.getOrganName().equals(recipient.getOrganName());
    }

    public DonorRecipientMatch matchDonorToRecipient(Donor donor) throws DonorNotFoundException, MatchNotFoundException{
        Optional<Donor> donor1 = this.donorDAO.findById(donor.getDonorId());
        if (donor1.isEmpty()){
            throw new DonorNotFoundException();
        }

        Donor d =donor1.get();

        List<Recipient> recipients = (List<Recipient>)this.recipientDAO.getAll(Sort.by(Sort.Direction.DESC, "priority"));
        for (Recipient r : recipients){
            DonorRecipientMatch donorRecipientMatch = donorRecipientMatchDAO.getMatchByRecipientId(r.getRecipientId());
            if (donorRecipientMatch == null){
                if (isMatch(d, r)){
                    DonorRecipientMatch donorRecipientMatchToAdd = new DonorRecipientMatch(d,r,0);
                    return addMatch(donorRecipientMatchToAdd);
                }
            }
        }
        throw new MatchNotFoundException();
    }
    public DonorRecipientMatch matchRecipientToDonor(Recipient recipient) throws RecipientNotFoundException, MatchNotFoundException{
        Optional<Recipient> recipient1 = this.recipientDAO.findById(recipient.getRecipientId());
        if (recipient1.isEmpty()){
            throw new RecipientNotFoundException();
        }

        Recipient r = recipient1.get();

        List<Donor> donors = (List<Donor>)this.donorDAO.findAll();
        for (Donor d : donors){
            DonorRecipientMatch donorRecipientMatch = donorRecipientMatchDAO.getMatchByDonorId(d.getDonorId());
            if (donorRecipientMatch == null){
                if (isMatch(d, r)){
                    DonorRecipientMatch donorRecipientMatchToAdd = new DonorRecipientMatch(d,r,0);
                    return addMatch(donorRecipientMatchToAdd);
                }
            }
        }
        throw new MatchNotFoundException();
    }

}
