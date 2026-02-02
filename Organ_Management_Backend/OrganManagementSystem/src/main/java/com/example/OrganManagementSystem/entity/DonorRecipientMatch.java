package com.example.OrganManagementSystem.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "matches")
public class DonorRecipientMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "rec_id")
    private Recipient recipient;

    @JoinColumn(name = "completed")
    private Integer completed;

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public DonorRecipientMatch(Integer id, Donor donor, Recipient recipient, Integer completed) {
        this.id = id;
        this.donor = donor;
        this.recipient = recipient;
        this.completed = completed;
    }

    public DonorRecipientMatch(Donor donor, Recipient recipient, Integer completed) {
        this.donor = donor;
        this.recipient = recipient;
        this.completed = completed;
    }

    public DonorRecipientMatch() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
