package com.example.util;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BaseballCoach implements Coach{

    public BaseballCoach(){
        System.out.println("In constructor: "+getClass().getSimpleName());
    }

    @PreDestroy
    public void doMoreStuff(){
        System.out.println("Finished: "+getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Batting practice for 30 minutes";
    }
}
