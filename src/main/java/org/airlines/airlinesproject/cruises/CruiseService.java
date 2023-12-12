package org.airlines.airlinesproject.cruises;

import com.paypal.api.payments.Currency;
import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.client.Client;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.airlines.airlinesproject.cruises.dto.CruiseResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CruiseService {

    private final CruiseRepository cruiseRepository;

    public Cruise createCruise(@NonNull CruiseRequest request){

        CruiseRequestValidation(request);

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

    public Cruise findById(@NonNull UUID id){
        if(Objects.isNull(id)){
            throw new IllegalArgumentException("Id can not be null!");
        }
        return cruiseRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cruise with id: " + id + " doesn't exist"));
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

    private static void CruiseRequestValidation(CruiseRequest request) {
        if(Objects.isNull(request)){
            throw new IllegalArgumentException("Request can not be null!");
        }

        if(Objects.isNull(request.getCruiseFrom())){
            throw new IllegalArgumentException("Cruise from can not be null!");
        }

        if(request.getCruiseFrom().isEmpty() || request.getCruiseFrom().isBlank()){
            throw new IllegalArgumentException("Cruise from can not be empty!");
        }

        if(Objects.isNull(request.getCruiseTo())){
            throw new IllegalArgumentException("Cruise to can not be null!");
        }

        if(request.getCruiseTo().isEmpty() || request.getCruiseTo().isBlank()){
            throw new IllegalArgumentException("Cruise to can not be empty!");
        }

        if(Objects.isNull(request.getDateOfStartCruise())){
            throw new IllegalArgumentException("Date of the start cruise can not be null!");
        }

        if(Objects.isNull(request.getCurrency())){
            throw new IllegalArgumentException("Currency can not be null!");
        }

        if(request.getCurrency().isEmpty() || request.getCurrency().isBlank()){
            throw new IllegalArgumentException("Cruise to can not be empty!");
        }

        if(request.getStandardPrice() < 1){
            throw new IllegalArgumentException("Standard price must be higher then 0!");
        }

        if(request.getNumberOfAvailableSeats() < 0){
            throw new IllegalArgumentException("Number of available seats must be higher or equal 0!");
        }

        final Date requestDate = request.getDateOfStartCruise();

        final LocalDateTime requestLocalDateTime = requestDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        final LocalDateTime localDateTime = LocalDateTime.now();

        final int comparedDate = requestLocalDateTime.compareTo(localDateTime);

        if(comparedDate < 0){
            throw new IllegalArgumentException(requestLocalDateTime + " is older then current time: " + localDateTime);
        }
    }



}
