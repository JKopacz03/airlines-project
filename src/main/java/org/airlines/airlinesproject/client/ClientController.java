package org.airlines.airlinesproject.client;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.client.dto.ClientNewPasswordRequest;
import org.airlines.airlinesproject.client.dto.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "/find-all-clients")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ClientResponse> findAllClients(){
        return clientService.findAll();
    }

    @GetMapping(path = "/find-by-email")
    @PreAuthorize("hasRole('ADMIN')")
    public ClientResponse findByEmail(@RequestParam String email){
        return clientService.findByEmail(email);
    }


}
