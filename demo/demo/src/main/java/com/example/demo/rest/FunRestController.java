package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
    //to expose it to the net
    @Value("${coach.name}")
    private String coachname;

    @Value("${team.name}")
    private String teamname;

    @GetMapping("/teaminfo")
    public String getInfo(){
        return "Coach Name: "+coachname+" Team Name: "+teamname;
    }

    @GetMapping("/")
    public String sayHello(){
        return "Hello World!";
    }

    //expose a new endpoint
    @GetMapping("/workout")
    public String getDailyWorkout() {
        return "Run!";
    }
}
