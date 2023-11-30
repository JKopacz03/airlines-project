package org.airlines.airlinesproject.cruises;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CruiseService {

    private final CruiseRepository cruiseRepository;



    public void createCruise(CruiseRequest request){

        final BigDecimal standardPrice = BigDecimal.valueOf(request.getStandardPrice());

        final Cruise cruise = new Cruise(
                UUID.randomUUID(),
                request.getCruiseFrom(),
                request.getCruiseTo(),
                request.getDateOfStartCruise(),
                standardPrice,
                request.getCurrency(),
                request.getNumberOfAvailableSeats()
        );

        cruiseRepository.save(cruise);
    }

}
