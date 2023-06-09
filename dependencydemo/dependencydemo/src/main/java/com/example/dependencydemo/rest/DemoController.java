package com.example.dependencydemo.rest;

import com.example.util.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    //a variable for the dependency

    private Coach myCoach;
    private Coach otherCoach;

    //define a constructor for dependency injection
    @Autowired
    public DemoController(@Qualifier("aquatic") Coach theCoach, @Qualifier("trackCoach") Coach otherCoach){
        System.out.println("In constructor: "+getClass().getSimpleName());
        myCoach = theCoach;
        this.otherCoach = otherCoach;
        //Autowired tells spring to inject a dependency
        //if only one constructor, @Autowired is optional
    }

    //instead of constructor, we can also do through some other method using @Autowired annotation

//    @Autowired
//    public setCoach(Coach theCoach){
//        myCoach = theCoach;
//    }

    //finally add an endpoint
    @GetMapping("/workout")
    public String getDailyWorkout() {
        return myCoach.getDailyWorkout();
    }

    @GetMapping("/check")
    public String checkCoaches(){
        return "The two coaches are equal: "+(myCoach == otherCoach);
    }

}
