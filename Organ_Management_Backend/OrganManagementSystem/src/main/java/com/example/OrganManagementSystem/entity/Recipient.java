package com.example.OrganManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "recipient")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rec_id")
    private Integer recipientId;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "patient_id")
    private PatientInformation patientInformation;

    @Column(name = "organ_requested")
    private String organName;

    @Column(name = "priority")
    private Integer priority;

    public Recipient() {
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Recipient(String organName, Integer priority) {
        this.organName = organName;
        this.priority = priority;
    }

    public Recipient(Integer id, String organName, Integer priority) {
        this.recipientId = id;
        this.organName = organName;
        this.priority = priority;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
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

