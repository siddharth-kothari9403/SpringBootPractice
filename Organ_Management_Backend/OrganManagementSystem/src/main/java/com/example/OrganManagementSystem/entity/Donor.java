package com.example.OrganManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "donor")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donor_id")
    private Integer donorId;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "patient_id")
    private PatientInformation patientInformation;

    @Column(name = "organ_donated")
    private String organName;

    public Donor() {
    }

    public Donor(String organName) {
        this.organName = organName;
    }

    public Donor(Integer id, String organName) {
        this.donorId = id;
        this.organName = organName;
    }

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public PatientInformation getPatientInformation() {
        return patientInformation;
    }

    public void setPatientInformation(PatientInformation patientInformation) {
        this.patientInformation = patientInformation;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
