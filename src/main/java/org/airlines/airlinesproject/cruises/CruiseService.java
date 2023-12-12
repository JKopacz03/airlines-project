package org.airlines.airlinesproject.cruises;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.client.Client;
import org.airlines.airlinesproject.client.ClientService;
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

    public List<CruiseResponse> findAllForAdmin(){
        final List<Cruise> cruises = cruiseRepository.findAll();
        if(cruises.isEmpty()){
            throw new IllegalStateException("Not existing cruises");
        }

        return cruises.stream()
                .map(this::mapCruiseToCruiseResponseForAdmin)
                .toList();
    }

    public List<CruiseResponse> findAll(){
        final List<Cruise> cruises = cruiseRepository.findAll();
        if(cruises.isEmpty()){
            throw new IllegalStateException("Not existing cruises");
        }

        return cruises.stream()
                .map(this::mapCruiseToCruiseResponse)
                .toList();
    }

    public CruiseResponse mapCruiseToCruiseResponseForAdmin(Cruise cruise){
        final List<Client> clients = cruise.getClients();

        return CruiseResponse.builder()
                .Id(cruise.getId().toString())
                .cruiseFrom(cruise.getCruiseFrom())
                .cruiseTo(cruise.getCruiseTo())
                .dateOfStartCruise(cruise.getDateOfStartCruise())
                .standardPrice(cruise.getStandardPrice().doubleValue())
                .currency(cruise.getCurrency().getCurrencyCode())
                .numberOfAvailableSeats(cruise.getNumberOfAvailableSeats())
                .clients(getClients(clients))
                .build();
    }

    private static List<String> getClients(List<Client> clients) {
        if(Objects.isNull(clients)){
            return null;
        }
        return clients.stream().map(Client::getEmail).toList();
    }

    public CruiseResponse mapCruiseToCruiseResponse(Cruise cruise){
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
    public void saveClientToCruise(@NonNull UUID cruiseId, @NonNull Client client){

        validateSaveClientToCruise(cruiseId, client);

        final Cruise cruise = cruiseRepository.findById(cruiseId).orElseThrow(() -> new IllegalStateException("Cruise doesn't exist"));

        final Client clientResponse = Client.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .build();

        if(!Objects.isNull(cruise.getClients()) && !cruise.getClients().isEmpty()) {
            final List<Client> cruisesClient = cruise.getClients();
            cruisesClient.add(clientResponse);

            cruise.setClients(cruisesClient);

            cruiseRepository.save(cruise);
        }

        if(Objects.isNull(cruise.getClients()) || cruise.getClients().isEmpty()){
            final ArrayList<Client> cruisesClient = new ArrayList<>();
            cruisesClient.add(clientResponse);

            cruise.setClients(cruisesClient);

            cruiseRepository.save(cruise);
        }


        //Subtraction of available seats

        modifyAmountOfAvailableSets(cruise);
    }

    private static void validateSaveClientToCruise(UUID cruiseId, Client client) {
        if(Objects.isNull(cruiseId)){
            throw new IllegalArgumentException("Cruise id can not be null!");
        }

        if(Objects.isNull(client)){
            throw new IllegalArgumentException("Client can not be null!");
        }

        if(Objects.isNull(client.getId())){
            throw new IllegalArgumentException("Id can not be null!");
        }

        if(Objects.isNull(client.getFirstName())){
            throw new IllegalArgumentException("First name can not be null!");
        }

        if(client.getFirstName().isEmpty() || client.getFirstName().isBlank()){
            throw new IllegalArgumentException("First name can not be empty!");
        }

        if(Objects.isNull(client.getLastName())){
            throw new IllegalArgumentException("Last name can not be null!");
        }

        if(client.getLastName().isEmpty() || client.getLastName().isBlank()){
            throw new IllegalArgumentException("Last name can not be empty!");
        }

        if(Objects.isNull(client.getEmail())){
            throw new IllegalArgumentException("Email can not be null!");
        }

        if(client.getEmail().isEmpty() || client.getEmail().isBlank()){
            throw new IllegalArgumentException("Email can not be empty!");
        }
    }

    public void modifyAmountOfAvailableSets(Cruise cruise) {
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
