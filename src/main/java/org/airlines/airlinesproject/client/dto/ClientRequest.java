package org.airlines.airlinesproject.client.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientRequest {
    private String email;
    private String currentPassword;
    private String newPassword;

}
