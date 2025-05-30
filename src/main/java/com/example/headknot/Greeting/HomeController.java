package com.example.headknot.Greeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    GreetingService greetingService;

    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        greetingService.createGreeting("Hello Sonu Nigam");
        greetingService.createGreeting("Hello Poonam Nigam");
        greetingService.createGreeting("Hello Maanya Nigam");
        GreetingEntity greeting = greetingService.getGreetingById(1L);
        return greeting.getMessage();
    }
}
