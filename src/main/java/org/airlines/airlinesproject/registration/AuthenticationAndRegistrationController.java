package org.airlines.airlinesproject.registration;

import lombok.AllArgsConstructor;
import org.airlines.airlinesproject.registration.authentication.dto.AuthenticationRequest;
import org.airlines.airlinesproject.registration.authentication.dto.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class AuthenticationAndRegistrationController {

    private final AuthenticationAndRegistrationService authenticationAndRegistrationService;

    @PostMapping(path = "/registration")
    public String register(@RequestBody RegistrationRequest request){
        return authenticationAndRegistrationService.register(request);
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationAndRegistrationService.authenticate(request));
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return authenticationAndRegistrationService.confirmToken(token);
    }
}
