package com.example.demod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DemodApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemodApplication.class, args);
	}

}
