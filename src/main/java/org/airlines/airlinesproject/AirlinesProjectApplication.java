package org.airlines.airlinesproject;

import org.airlines.airlinesproject.authenticationAndRegistration.AuthenticationAndRegistrationService;
import org.airlines.airlinesproject.authenticationAndRegistration.registration.dto.RegistrationRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import static org.airlines.airlinesproject.client.AppUserRole.ADMIN;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AirlinesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirlinesProjectApplication.class, args);
	}
//
//	@Bean
//	public CommandLineRunner commandLineRunner(AuthenticationAndRegistrationService service) {
//		return args -> {
//			var admin = RegistrationRequest.builder()
//					.firstName("Admin")
//					.lastName("Admin")
//					.email("admin2@mail.com")
//					.password("password")
//					.role(ADMIN)
//					.build();
//			System.out.println("Admin token: " + service.register(admin).getToken());
//		};
//
//	}

}
