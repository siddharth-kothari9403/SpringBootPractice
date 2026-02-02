package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import com.example.OrganManagementSystem.exception.UnauthorisedUserException;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.example.OrganManagementSystem.service.MatchService;
import com.example.OrganManagementSystem.service.PatientService;
import com.example.OrganManagementSystem.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/recipient")
public class RecipientRestController {
    private RecipientService recipientService;
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private MatchService matchService;
    private PatientService patientService;

    @Autowired
    public RecipientRestController(PatientService patientService, RecipientService recipientService, JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil, MatchService matchService){
        this.recipientService = recipientService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.matchService = matchService;
        this.patientService = patientService;
    }

    @GetMapping("/viewInfo")
    public List<Recipient> viewRecipient(@RequestHeader String Authorization){
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        PatientInformation patientInformation = this.patientService.viewPatientByUserId(user.getId());
        return recipientService.getRecipientByPatientId(patientInformation.getPatientId());
    }

    @GetMapping("/viewInfo/{id}")
    public Optional<Recipient> viewRecipientById(@PathVariable Integer id, @RequestHeader String Authorization) throws RecipientNotFoundException, UnauthorisedUserException {
        User user = this.jwtUserDetailsService.getUserByUsername(this.jwtTokenUtil.getUsernameFromToken(Authorization.substring(7)));
        Optional<Recipient> recipient = recipientService.viewInfoById(id);

        PatientInformation patientInformation = this.patientService.viewPatientByUserId(user.getId());

        if (recipient.isEmpty()){
            throw new RecipientNotFoundException();
        }else if (!Objects.equals(recipient.get().getPatientInformation().getPatientId(), patientInformation.getPatientId())){
            throw new UnauthorisedUserException();
        }

        return recipient;
    }

    @PostMapping("/addInfo/{patientId}")
    public DonorRecipientMatch addRecipientInfo(@PathVariable Integer patientId, @RequestHeader String Authorization, @RequestBody Recipient recipient){
        Optional<PatientInformation> patientInformation = patientService.viewPatientInfo(patientId);
        if (patientInformation.isEmpty()){
            throw new PatientNotFoundException();
        }
        recipient.setPatientInformation(patientInformation.get());

        Recipient r = recipientService.addInfo(recipient);
        return matchService.matchRecipientToDonor(r);
    }

    @PutMapping("/updateInfo")
    public Recipient updateRecipientInfo(@RequestHeader String Authorization, @RequestBody Recipient recipient) throws RecipientNotFoundException{
        Optional<Recipient> recipient1 = this.recipientService.viewInfoById(recipient.getRecipientId());

        if (recipient1.isEmpty()){
            throw new RecipientNotFoundException();
        }

        recipient.setOrganName(recipient1.get().getOrganName());
        recipient.setPatientInformation(recipient1.get().getPatientInformation());
        return recipientService.updateInfo(recipient);
    }

    @GetMapping("/getAll")
    public List<Recipient> getAll(){
        return recipientService.getAllRecipients();
    }
}

