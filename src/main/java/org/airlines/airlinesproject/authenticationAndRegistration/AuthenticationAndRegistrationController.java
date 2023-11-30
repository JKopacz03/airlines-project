package org.airlines.airlinesproject.authenticationAndRegistration;

import lombok.AllArgsConstructor;
import org.airlines.airlinesproject.authenticationAndRegistration.authentication.dto.AuthenticationRequest;
import org.airlines.airlinesproject.authenticationAndRegistration.authentication.dto.AuthenticationResponse;
import org.airlines.airlinesproject.authenticationAndRegistration.registration.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class AuthenticationAndRegistrationController {

    private final AuthenticationAndRegistrationService authenticationAndRegistrationService;

    @PostMapping(path = "/registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(authenticationAndRegistrationService.register(request));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationAndRegistrationService.authenticate(request));
    }

}
