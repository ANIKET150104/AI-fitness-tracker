package com.fitness.activity_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivityServiceApplication {

	public static void main(String[] args) {
        System.setProperty("spring.data.mongodb.uri", "mongodb://localhost:27017/fitnessActivity");
		SpringApplication.run(ActivityServiceApplication.class, args);
	}

}
