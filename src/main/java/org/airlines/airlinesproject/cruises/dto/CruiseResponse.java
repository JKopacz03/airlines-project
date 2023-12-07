package org.airlines.airlinesproject.cruises.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.airlines.airlinesproject.client.Client;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CruiseResponse {

    private String Id;
    private String cruiseFrom;
    private String cruiseTo;
    private Date dateOfStartCruise;
    private double standardPrice;
    private String currency;
    private int numberOfAvailableSeats;
    private List<String> clients;

    public CruiseResponse(String id,
                          String cruiseFrom,
                          String cruiseTo,
                          Date dateOfStartCruise,
                          double standardPrice,
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
}
