package com.example.dependencydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (
	scanBasePackages = {"com.example.dependencydemo",
						"com.example.util"})

public class DependencydemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DependencydemoApplication.class, args);
	}
}
