package com.example.OrganManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = { CascadeType.DETACH,
            CascadeType.REFRESH })
    @JsonIgnore
    private DoctorInformation doctorInformation;

    @OneToOne(mappedBy = "user", cascade = { CascadeType.DETACH,
            CascadeType.REFRESH })
    @JsonIgnore
    private PatientInformation patientInformation;

    public DoctorInformation getDoctorInformation() {
        return doctorInformation;
    }

    public void setDoctorInformation(DoctorInformation doctorInformation) {
        this.doctorInformation = doctorInformation;
    }

    public PatientInformation getPatientInformation() {
        return patientInformation;
    }

    public void setPatientInformation(PatientInformation patientInformation) {
        this.patientInformation = patientInformation;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
