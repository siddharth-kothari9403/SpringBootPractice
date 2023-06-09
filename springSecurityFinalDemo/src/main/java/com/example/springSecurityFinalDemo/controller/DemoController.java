package com.example.springSecurityFinalDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("/")
    public String displayHome(){
        return "home";
    }

    @GetMapping("/leaders")
    public String displayLeadersPage(){
        return "leaders";
    }

    @GetMapping("/systems")
    public String displaySystems(){
        return "systems";
    }
}
