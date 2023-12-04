package org.airlines.airlinesproject.cruises;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CruiseService {

    private final CruiseRepository cruiseRepository;

    public Cruise createCruise(CruiseRequest request){

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

        return cruise;
    }

    public Cruise findById(UUID id){
        return cruiseRepository.findById(id).orElseThrow();
    }

    public void modifyAmountOfAvailableSeats(UUID idOfCruise, int newNumberOfAvailableSeats){
        Cruise cruise = cruiseRepository.findById(idOfCruise).orElseThrow(
                () -> new IllegalStateException("can't find user by this id"));
        cruise.setNumberOfAvailableSeats(newNumberOfAvailableSeats);
        cruiseRepository.save(cruise);
    }






}
