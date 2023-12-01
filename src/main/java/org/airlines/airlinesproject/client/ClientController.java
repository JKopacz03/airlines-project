package org.airlines.airlinesproject.client;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.client.dto.ClientNewPasswordRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/modify/password")
    public void modifyPassword(@RequestBody ClientNewPasswordRequest request) {
        clientService.modifyPassword(request);
    }
}
