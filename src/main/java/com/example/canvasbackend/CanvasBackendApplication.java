package com.example.canvasbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.canvasbackend.repository.EnrollmentRepository;

@SpringBootApplication
public class CanvasBackendApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CanvasBackendApplication.class, args);

		// Initialize Enrollment table
		EnrollmentRepository enrollmentRepository = context.getBean(EnrollmentRepository.class);
		// You can perform any initialization or checks here if needed
	}
}
