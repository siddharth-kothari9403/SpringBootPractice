package com.example.mvcDemo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//the demo mvc controller
@Controller
public class DemoController {

    @GetMapping("/hello")
    public String sayHello(Model model){
        model.addAttribute("date", new java.util.Date());

        //the return is actually the template needed from templates directory
        //the attribute set is then accessible by its name in the template
        return "helloWorld";
    }
}
