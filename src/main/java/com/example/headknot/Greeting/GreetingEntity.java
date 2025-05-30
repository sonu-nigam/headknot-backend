package com.example.headknot.Greeting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "greeting")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreetingEntity {
    @Id
    private Long id;

    private String message;
}

