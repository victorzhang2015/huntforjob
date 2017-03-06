package com.victor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    private Test people;

    @RequestMapping("/hello")
    public String index() {
        return people.getName();
    }
}