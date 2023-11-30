package org.airlines.airlinesproject.cruises.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.airlines.airlinesproject.cruises.Airports;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

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
