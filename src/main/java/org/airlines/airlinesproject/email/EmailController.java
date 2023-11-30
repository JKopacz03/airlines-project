package org.airlines.airlinesproject.email;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.registration.AuthenticationAndRegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/email")
@RequiredArgsConstructor
public class EmailController {

    private final AuthenticationAndRegistrationService authenticationAndRegistrationService;

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authenticationAndRegistrationService.confirmToken(token);
    }

}
