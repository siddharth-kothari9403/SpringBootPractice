package com.example.OrganManagementSystem.controller;

import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/match")
public class MatchRestController {

    private MatchService matchService;

    @Autowired
    public MatchRestController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping("/donor/{id}")
    public DonorRecipientMatch getByDonorId(@PathVariable Integer id){
        return matchService.getMatchByDonorId(id);
    }

    @GetMapping("/recipient/{id}")
    public DonorRecipientMatch getByRecipientId(@PathVariable Integer id){
        return matchService.getMatchByRecipientId(id);
    }

    @GetMapping("/patient/donor/{id}")
    public List<DonorRecipientMatch> getByDonorPatientId(@PathVariable Integer id){
        return matchService.getMatchesByDonorPatientId(id);
    }

    @GetMapping("/patient/recipient/{id}")
    public List<DonorRecipientMatch> getByRecipientPatientId(@PathVariable Integer id){
        return matchService.getMatchesByRecipientPatientId(id);
    }
}
