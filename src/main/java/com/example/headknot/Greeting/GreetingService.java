package com.example.headknot.Greeting;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @Autowired
    private GreetingRepository greetingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public GreetingEntity getGreetingById(Long greetingId) {
        return greetingRepository.findById(greetingId).orElse(null);
    }

    public GreetingEntity createGreeting(String message) {
        GreetingEntity greeting = new GreetingEntity();
        greeting.setMessage(message);
        return greetingRepository.save(greeting);
    }

}
