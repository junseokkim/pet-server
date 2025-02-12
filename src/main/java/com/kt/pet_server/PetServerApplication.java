package com.kt.pet_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetServerApplication.class, args);
	}

}
