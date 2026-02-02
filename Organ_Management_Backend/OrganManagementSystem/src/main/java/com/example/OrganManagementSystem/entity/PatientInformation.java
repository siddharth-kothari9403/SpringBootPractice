package com.example.OrganManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "patient_info")
public class PatientInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Integer patientId;

    @OneToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "pname")
    private String pname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "mobile_number")
    private String phoneNo;

    @Column(name = "age")
    private int age;

    @Column(name = "blood_type")
    private String bloodType;

    public PatientInformation(String pname, String gender, String phoneNo, Integer age, String bloodGroup) {
        this.pname = pname;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.age = age;
        this.bloodType = bloodGroup;
    }

    public PatientInformation(Integer id, String pname, String gender, String phoneNo, Integer age, String bloodGroup) {
        this.patientId = id;
        this.pname = pname;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.age = age;
        this.bloodType = bloodGroup;
    }

    public PatientInformation() {

    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer reg_id) {
        this.patientId = reg_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String fname) {
        this.pname = fname;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}