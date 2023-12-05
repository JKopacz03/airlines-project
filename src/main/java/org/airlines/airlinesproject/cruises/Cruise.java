package org.airlines.airlinesproject.cruises;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.airlines.airlinesproject.client.Client;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Cruise {

    @Id
    private UUID Id;
    private String cruiseFrom;
    private String cruiseTo;
    private Date dateOfStartCruise;
    private BigDecimal standardPrice;
    private String currency;
    private int numberOfAvailableSeats;
    @ManyToMany
    @JoinTable(
            name = "passengers_of_cruise",
            joinColumns = {@JoinColumn(name = "cruise_id")},
            inverseJoinColumns = {@JoinColumn(name = "client_id")}
    )
    private List<Client> clients;

    public Cruise(UUID id,
                  String cruiseFrom,
                  String cruiseTo,
                  Date dateOfStartCruise,
                  BigDecimal standardPrice,
                  String currency,
                  int numberOfAvailableSeats) {
        Id = id;
        this.cruiseFrom = cruiseFrom;
        this.cruiseTo = cruiseTo;
        this.dateOfStartCruise = dateOfStartCruise;
        this.standardPrice = standardPrice;
        this.currency = currency;
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency.getCurrencyCode();
    }

    public Currency getCurrency() {
        return Currency.getInstance(currency);
    }

}
