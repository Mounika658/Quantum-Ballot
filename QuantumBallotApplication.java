package com.quantumballot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Main Application Entry Point
 * Initiates the Quantum Cryptographic Voting Backend Service.
 */
@SpringBootApplication
public class QuantumBallotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantumBallotApplication.class, args);
        System.out.println("=================================================");
        System.out.println("⚛️ Quantum Ballot Protocol Java Engine Active");
        System.out.println("=================================================");
    }
}