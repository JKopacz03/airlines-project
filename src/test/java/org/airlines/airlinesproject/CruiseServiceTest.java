package org.airlines.airlinesproject;

import org.airlines.airlinesproject.client.Client;
import org.airlines.airlinesproject.client.ClientService;
import org.airlines.airlinesproject.cruises.Cruise;
import org.airlines.airlinesproject.cruises.CruiseRepository;
import org.airlines.airlinesproject.cruises.CruiseService;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.airlines.airlinesproject.cruises.dto.CruiseResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CruiseServiceTest {

    private CruiseRepository cruiseRepository;
    private CruiseService cruiseService;


    @BeforeEach
    public void setup() {
        cruiseRepository = mock(CruiseRepository.class);
        cruiseService =  new CruiseService(cruiseRepository);
    }

    @Test
    public void createCruise_nullRequest_throwsIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(null));
    }

    @Test
    public void createCruise_nullCruiseFrom_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                null,
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_emptyCruiseFrom_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_blankCruiseFrom_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "    ",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_nullCruiseTo_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                null,
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_emptyCruiseTo_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_blankCruiseTo_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "   ",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_nullDateOfStartCruise_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                null,
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_nullCurrency_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                null,
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_emptyCurrency_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_blankCurrency_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "    ",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_outOfDateOfStartCruise_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2023, Calendar.DECEMBER, 10, 12, 30).getTime(),
                10000,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_invalidStandardPrice_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                -10,
                "PLN",
                30
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

    @Test
    public void createCruise_invalidNumberOfAvailableSeats_throwsIllegalArgumentException(){
        //given
        final CruiseRequest cruiseRequest = new CruiseRequest(
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                1000,
                "PLN",
                -1
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.createCruise(cruiseRequest));
    }

//    test for finById

    @Test
    public void findById_findingCruise_cruiseFounded(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Cruise cruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                30
        );
        when(cruiseRepository.findById(cruiseId)).thenReturn(Optional.of(cruise));
        //when
        final Cruise actualCruise = cruiseService.findById(cruiseId);
        //then
        Assertions.assertEquals(cruise, actualCruise);
    }

    @Test
    public void findById_notExistingCruise_throwIllegalStateException(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        //when/then
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> cruiseService.findById(cruiseId));
    }

    @Test
    public void findById_nullId_throwsIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.findById(null));
    }

//    Tests for findAllForAdmin

    @Test
    public void findByAllForAdmin_findingAllCruises_allCruisesFounded(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Cruise cruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                30
        );
        cruise.setClients(List.of(new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                "example@mail.com"
                )));
        final ArrayList<Cruise> cruises = new ArrayList<>();
        cruises.add(cruise);

        when(cruiseRepository.findAll()).thenReturn(cruises);
        //when
        final List<CruiseResponse> actualCruises = cruiseService.findAllForAdmin();
        //then
        final List<CruiseResponse> expectedCruises = cruises.stream()
                .map(cruise1 -> cruiseService.mapCruiseToCruiseResponseForAdmin(cruise1))
                .toList();

        Assertions.assertEquals(expectedCruises, actualCruises);
    }

    @Test
    public void findByAllForAdmin_findingAllCruisesButListOfClientsIsNull_allCruisesFounded(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Cruise cruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                30
        );
        final ArrayList<Cruise> cruises = new ArrayList<>();
        cruises.add(cruise);

        when(cruiseRepository.findAll()).thenReturn(cruises);
        //when
        final List<CruiseResponse> actualCruises = cruiseService.findAllForAdmin();
        //then
        final List<CruiseResponse> expectedCruises = cruises.stream()
                .map(cruise1 -> cruiseService.mapCruiseToCruiseResponseForAdmin(cruise1))
                .toList();

        Assertions.assertEquals(expectedCruises, actualCruises);
    }

    @Test
    public void findByAllForAdmin_notExistingCruises_throwIllegalStateException(){
        //given
        //when/then
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> cruiseService.findAllForAdmin()
        );
    }

//    Tests for findAll

    @Test
    public void findByAll_findingAllCruises_allCruisesFounded(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Cruise cruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                30
        );
        final ArrayList<Cruise> cruises = new ArrayList<>();
        cruises.add(cruise);

        when(cruiseRepository.findAll()).thenReturn(cruises);
        //when
        final List<CruiseResponse> actualCruises = cruiseService.findAll();
        //then
        final List<CruiseResponse> expectedCruises = cruises.stream()
                .map(cruise1 -> cruiseService.mapCruiseToCruiseResponse(cruise1))
                .toList();

        Assertions.assertEquals(expectedCruises, actualCruises);
    }

    @Test
    public void findByAll_notExistingCruises_throwIllegalStateException(){
        //given
        //when/then
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> cruiseService.findAllForAdmin()
        );
    }

//    Tests for saveClientToCruise

    @Test
    public void saveClientToCruise_savingClientToCruise_clientSavedToCruise(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Cruise cruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                30
        );
        final Client client = new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                "example@mail.com"
        );
        when(cruiseRepository.findById(cruiseId)).thenReturn(Optional.of(cruise));
        //when
        cruiseService.saveClientToCruise(cruiseId, client);
        //then
        final Cruise expectedCruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                29
        );
        final ArrayList<Client> clients = new ArrayList<>();
        clients.add(client);
        expectedCruise.setClients(clients);
        verify(cruiseRepository, atMostOnce()).save(expectedCruise);
    }

    @Test
    public void saveClientToCruise_savingClientToCruiseWithExistingListOfClients_clientSavedToCruise(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Cruise cruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                30
        );
        final Client josef = new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                "example@mail.com"
        );
        final Client mathew = new Client(UUID.randomUUID(),
                "Mathew",
                "Scott",
                "example2@mail.com"
        );
        final ArrayList<Client> alreadyClients = new ArrayList<>();
        alreadyClients.add(mathew);
        cruise.setClients(alreadyClients);
        when(cruiseRepository.findById(cruiseId)).thenReturn(Optional.of(cruise));
        //when
        cruiseService.saveClientToCruise(cruiseId, josef);
        //then
        final Cruise expectedCruise = new Cruise(
                cruiseId,
                "Warsaw",
                "Tokyo",
                new GregorianCalendar(2123, Calendar.DECEMBER, 10, 12, 30).getTime(),
                BigDecimal.valueOf(1000),
                "PLN",
                29
        );
        final ArrayList<Client> clients = new ArrayList<>();
        clients.add(mathew);
        clients.add(josef);
        expectedCruise.setClients(clients);
        verify(cruiseRepository, atMostOnce()).save(expectedCruise);
    }

    @Test
    public void saveClientToCruise_notExistingCruise_throwIllegalStateException(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Client client = new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                "example@mail.com"
        );
        //when/then
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> cruiseService.saveClientToCruise(cruiseId, client)
        );
    }

    @Test
    public void saveClientToCruise_nullCruiseId_throwIllegalArgumentException(){
        //given
        final Client client = new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                "example@mail.com"
        );
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> cruiseService.saveClientToCruise(null, client)
        );
    }





}
