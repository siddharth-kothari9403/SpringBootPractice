package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminRestController {

    private AdminService adminService;

    @Autowired
    public AdminRestController(AdminService theAdminService) {
        this.adminService = theAdminService;
    }

    @GetMapping("/doctors")
    public List<DoctorInformation> findAllDocs() {
        return adminService.showDoctors();
    }

    @GetMapping("/patients")
    public List<PatientInformation> findAllPatients() {
        return adminService.showPatients();
    }

    @GetMapping("/doctors/{doctorId}")
    public Optional<DoctorInformation> getDoctorInformation(@PathVariable Integer doctorId) {
        return adminService.showDoctorById(doctorId);
    }

    @GetMapping("/patients/{patientId}")
    public Optional<PatientInformation> getPatientInformation(@PathVariable Integer patientId){
        return adminService.showPatientById(patientId);
    }
}
