package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.entity.*;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.UnauthorisedUserException;
import com.example.OrganManagementSystem.service.DonorService;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.example.OrganManagementSystem.service.MatchService;
import com.example.OrganManagementSystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/donor")
public class DonorRestController {
    private DonorService donorService;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private MatchService matchService;
    private PatientService patientService;

    @Autowired
    public DonorRestController(DonorService donorService, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil, MatchService matchService, PatientService patientService){
        this.donorService = donorService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.matchService = matchService;
        this.patientService = patientService;
    }

    @GetMapping("/viewInfo")
    public List<Donor> viewDonor(@RequestHeader String Authorization){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        PatientInformation patientInformation = patientService.viewPatientByUserId(user.getId());
        return donorService.getDonorByPatientId(patientInformation.getPatientId());
    }

    @GetMapping("/viewInfo/{id}")
    public Optional<Donor> viewDonorById(@PathVariable Integer id, @RequestHeader String Authorization) throws DonorNotFoundException, UnauthorisedUserException {
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        Optional<Donor> donor = donorService.viewInfoById(id);

        PatientInformation patientInformation = patientService.viewPatientByUserId(user.getId());

        if (donor.isEmpty()){
            throw new DonorNotFoundException();
        }else if (!Objects.equals(donor.get().getPatientInformation().getPatientId(), patientInformation.getPatientId())){
            throw new UnauthorisedUserException();
        }

        return donor;
    }

    @PostMapping("/addInfo/{patientId}")
    public DonorRecipientMatch addDonorInfo(@PathVariable Integer patientId, @RequestBody Donor donor) throws PatientNotFoundException{
        Optional<PatientInformation> patientInformation = patientService.viewPatientInfo(patientId);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        donor.setPatientInformation(patientInformation.get());
        Donor d = donorService.addInfo(donor);
        return matchService.matchDonorToRecipient(d);
    }

    @GetMapping("/getAll")
    public List<Donor> getAll(){
        return donorService.getAllDonors();
    }
}
