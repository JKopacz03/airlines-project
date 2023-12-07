package org.airlines.airlinesproject.client.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.airlines.airlinesproject.client.Role;
import org.airlines.airlinesproject.cruises.Cruise;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ClientResponse {

    private String firstName;
    private String lastName;
    private String email;
    private List<Cruise> cruises;

}
