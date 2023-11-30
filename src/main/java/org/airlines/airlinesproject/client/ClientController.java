package org.airlines.airlinesproject.client;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.authenticationAndRegistration.authentication.dto.AuthenticationRequest;
import org.airlines.airlinesproject.authenticationAndRegistration.authentication.dto.AuthenticationResponse;
import org.airlines.airlinesproject.client.dto.ClientRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/modify/password")
    public void modifyPassword(@RequestBody ClientRequest request) {
        clientService.modifyPassword(request);
    }
}
