package com.example.OrganManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "doctor_info")
public class DoctorInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "phone")
    private String phoneNo;

    public DoctorInformation(String doctorName, String speciality, String phoneNo) {
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.phoneNo = phoneNo;
    }

    public DoctorInformation(Integer id, String doctorName, String speciality, String phoneNo) {
        this.doctorId = id;
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.phoneNo = phoneNo;
    }

    public DoctorInformation(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}

