package org.airlines.airlinesproject.authenticationAndRegistration.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
}
