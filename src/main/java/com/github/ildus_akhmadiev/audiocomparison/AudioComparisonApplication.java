package com.github.ildus_akhmadiev.audiocomparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class AudioComparisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AudioComparisonApplication.class, args);
    }

}
