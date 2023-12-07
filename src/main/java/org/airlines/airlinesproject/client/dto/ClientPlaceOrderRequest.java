package org.airlines.airlinesproject.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ClientPlaceOrderRequest {

    public UUID cruiseId;
    private String firstName;
    private String lastName;
    private String email;

}
