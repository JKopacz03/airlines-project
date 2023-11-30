package org.airlines.airlinesproject.cruises;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.airlines.airlinesproject.client.Client;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Cruises {

    @Id
    private UUID Id;
    @Enumerated(EnumType.STRING)
    private Airports cruiseFrom;
    @Enumerated(EnumType.STRING)
    private Airports cruiseTo;
    private Date dateOfStartCruise;
    private BigDecimal standardPrice;
    private int numberOfAvailableSeats;
    private boolean isAvailable;
    @ManyToMany
    @JoinTable(
            name = "passengers_of_cruise",
            joinColumns = {@JoinColumn(name = "cruise_id")},
            inverseJoinColumns = {@JoinColumn(name = "client_id")}
    )
    private List<Client> clients;

}
