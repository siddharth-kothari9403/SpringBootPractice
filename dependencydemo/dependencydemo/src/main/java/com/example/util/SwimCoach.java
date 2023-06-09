package com.example.util;

//@Configuration and @Bean are used to create beans from 3rd party classes, whose source code you may not have
public class SwimCoach implements Coach{

    public SwimCoach(){
        System.out.println("In constructor: "+getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout() {
        return "Swim 100 meters.";
    }
}
