package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.service.DoctorService;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorRestController {

    private DoctorService doctorService;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    public DoctorRestController(DoctorService doctorService, JwtUserDetailsService theJwtUserDetailsService, JwtTokenUtil theJwtTokenUtil) {
        this.doctorService = doctorService;
        this.jwtUserDetailsService = theJwtUserDetailsService;
        this.jwtTokenUtil = theJwtTokenUtil;
    }

    @GetMapping("/viewPatients")
    public List<PatientInformation> viewPatients(@RequestHeader String Authorization){
        return doctorService.showPatients();
    }

    @GetMapping("/viewPatients/{id}")
    public Optional<PatientInformation> viewPatientById(@PathVariable Integer id, @RequestHeader String Authorization){
        return doctorService.showPatientById(id);
    }

    @GetMapping("/viewMyInfo")
    public DoctorInformation viewMyInfo(@RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        return doctorService.getDoctorByUserId(user.getId());
    }

    @PutMapping("/updateMyInfo")
    public DoctorInformation updateMyInfo(@RequestBody DoctorInformation doctorInformation, @RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        DoctorInformation doctorInformation1 = doctorService.getDoctorByUserId(user.getId());
        doctorInformation.setUser(user);
        doctorInformation.setDoctorId(doctorInformation1.getDoctorId());
        return doctorService.updateMyInfo(doctorInformation);
    }

    @PostMapping("/addMyInfo")
    public DoctorInformation addDocInfo(@RequestBody DoctorInformation doctorInformation, @RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        doctorInformation.setUser(user);
        return doctorService.addDocInfo(doctorInformation);
    }
}

