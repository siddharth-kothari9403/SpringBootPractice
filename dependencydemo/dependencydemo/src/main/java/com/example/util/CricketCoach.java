package com.example.util;

import org.springframework.stereotype.Component;

//component annotation marks it for dependency injection
@Component
public class CricketCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Practice daily bowling for 15 minutes!";
    }
}
