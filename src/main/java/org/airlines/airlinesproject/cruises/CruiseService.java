package org.airlines.airlinesproject.cruises;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.client.Client;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.airlines.airlinesproject.cruises.dto.CruiseResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    public List<CruiseResponse> findAllForAdmin(){
        final List<Cruise> cruises = cruiseRepository.findAll();

        return cruises.stream()
                .map(this::mapCruiseToCruiseResponseForAdmin)
                .toList();
    }

    public List<CruiseResponse> findAll(){
        final List<Cruise> cruises = cruiseRepository.findAll();

        return cruises.stream()
                .map(this::mapCruiseToCruiseResponse)
                .toList();
    }

    private CruiseResponse mapCruiseToCruiseResponseForAdmin(Cruise cruise){
        final List<Client> clients = cruise.getClients();

        return CruiseResponse.builder()
                .Id(cruise.getId().toString())
                .cruiseFrom(cruise.getCruiseFrom())
                .cruiseTo(cruise.getCruiseTo())
                .dateOfStartCruise(cruise.getDateOfStartCruise())
                .standardPrice(cruise.getStandardPrice().doubleValue())
                .currency(cruise.getCurrency().getCurrencyCode())
                .numberOfAvailableSeats(cruise.getNumberOfAvailableSeats())
                .clients(clients.stream().map(Client::getEmail).toList())
                .build();
    }
    private CruiseResponse mapCruiseToCruiseResponse(Cruise cruise){
        return CruiseResponse.builder()
                .Id(cruise.getId().toString())
                .cruiseFrom(cruise.getCruiseFrom())
                .cruiseTo(cruise.getCruiseTo())
                .dateOfStartCruise(cruise.getDateOfStartCruise())
                .standardPrice(cruise.getStandardPrice().doubleValue())
                .currency(cruise.getCurrency().getCurrencyCode())
                .numberOfAvailableSeats(cruise.getNumberOfAvailableSeats())
                .build();
    }

    public void saveClientToCruise(UUID cruiseId, Client client){

        final Cruise cruise = cruiseRepository.findById(cruiseId).orElseThrow();

        final Client clientResponse = Client.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .build();

        if(!cruise.getClients().isEmpty()) {
            final List<Client> cruisesClient = cruise.getClients();
            cruisesClient.add(clientResponse);

            cruise.setClients(cruisesClient);

            cruiseRepository.save(cruise);
        }

        if(cruise.getClients().isEmpty()){
            final ArrayList<Client> cruisesClient = new ArrayList<>();
            cruisesClient.add(clientResponse);

            cruise.setClients(cruisesClient);

            cruiseRepository.save(cruise);
        }


        //Subtraction of available seats

        final int numberOfAvailableSeats = cruise.getNumberOfAvailableSeats();

        cruise.setNumberOfAvailableSeats(numberOfAvailableSeats - 1);

        cruiseRepository.save(cruise);
    }



}
