package org.airlines.airlinesproject;

import org.airlines.airlinesproject.cruises.Cruise;
import org.airlines.airlinesproject.cruises.CruiseRepository;
import org.airlines.airlinesproject.cruises.CruiseService;
import org.airlines.airlinesproject.cruises.dto.CruiseRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

}
