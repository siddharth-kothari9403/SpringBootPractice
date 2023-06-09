package com.example.dependencydemo.config;

import com.example.util.Coach;
import com.example.util.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//the class that create a bean from the SwimCoach class
@Configuration
public class SportConfig {

    @Bean("aquatic")
    public Coach swimCoach(){
        return new SwimCoach();
    }
}
