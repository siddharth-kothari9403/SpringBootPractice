package com.example.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//prototype beans are lazy by default
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TrackCoach implements Coach{

    public TrackCoach(){
        System.out.println("In constructor: "+getClass().getSimpleName());
    }

    @PostConstruct
    public void doStuff(){
        System.out.println("Startup completed for : "+getClass().getSimpleName());
    }

    @PreDestroy
    public void doMoreStuff(){
        System.out.println("Finished: "+getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Run 5 km!";
    }
}
