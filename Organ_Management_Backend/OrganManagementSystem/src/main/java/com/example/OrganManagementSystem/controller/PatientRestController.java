package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.example.OrganManagementSystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class PatientRestController {

    private PatientService patientService;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public PatientRestController(PatientService patientService, JwtUserDetailsService theJwtUserDetailsService, JwtTokenUtil theJwtTokenUtil) {
        this.patientService = patientService;
        this.jwtUserDetailsService = theJwtUserDetailsService;
        this.jwtTokenUtil = theJwtTokenUtil;
    }

    @PostMapping("/addPatientInfo")
    public PatientInformation addPatientInfo(@RequestBody PatientInformation theInfo, @RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        theInfo.setUser(user);
        return patientService.addPatientInfo(theInfo);
    }

    @PutMapping("/updateMyInfo")
    public PatientInformation updatePatientInfo(@RequestBody PatientInformation theInfo, @RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        PatientInformation patientInformation = patientService.viewPatientByUserId(user.getId());
        theInfo.setUser(user);
        theInfo.setPatientId(patientInformation.getPatientId());
        theInfo.setBloodType(patientInformation.getBloodType());
        return patientService.updatePatientInfo(theInfo);
    }

    @GetMapping("/viewMyInfo")
    public PatientInformation viewPatientInfo(@RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        return this.patientService.viewPatientByUserId(user.getId());
    }
}
