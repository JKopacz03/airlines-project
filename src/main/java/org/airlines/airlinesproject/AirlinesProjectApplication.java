package org.airlines.airlinesproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AirlinesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlinesProjectApplication.class, args);
	}

}
