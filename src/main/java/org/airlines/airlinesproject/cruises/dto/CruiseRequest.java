package org.airlines.airlinesproject.cruises.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CruiseRequest {

    private String cruiseFrom;
    private String cruiseTo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateOfStartCruise;
    private double standardPrice;
    private String currency;
    private int numberOfAvailableSeats;
}
