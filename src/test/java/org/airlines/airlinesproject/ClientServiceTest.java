package org.airlines.airlinesproject;

import org.airlines.airlinesproject.authenticationAndRegistration.token.ConfirmationTokenService;
import org.airlines.airlinesproject.client.Client;
import org.airlines.airlinesproject.client.ClientRepository;
import org.airlines.airlinesproject.client.ClientService;
import org.airlines.airlinesproject.client.Role;
import org.airlines.airlinesproject.cruises.Cruise;
import org.airlines.airlinesproject.cruises.CruiseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final ConfirmationTokenService confirmationTokenService = mock(ConfirmationTokenService.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final CruiseService cruiseService = mock(CruiseService.class);

    @BeforeEach
    public void setup() {
        clientRepository = mock(ClientRepository.class);

        clientService = new ClientService(clientRepository,
                passwordEncoder,
                confirmationTokenService,
                authenticationManager,
                cruiseService
        );
    }

//    Test for loadByUserName method

    @Test
    public void loadByUserName_returnClient_clientReturned(){
        //given
        String email = "example@mail.com";

        final Client client = new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                true,
                Collections.singletonList(new Cruise(UUID.randomUUID(),
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        //when
        final UserDetails actualClient = clientService.loadUserByUsername(email);
        //then
        Assertions.assertEquals(client, actualClient);
    }

    @Test
    public void loadByUserName_randomCase_clientReturned(){
        // given
        String email = "exAmplE@mail.com";

        final Client client = new Client(UUID.randomUUID(),
                "Josef",
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                true,
                Collections.singletonList(new Cruise(UUID.randomUUID(),
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        //when
        final UserDetails actualClient = clientService.loadUserByUsername(email);
        //then
        Assertions.assertEquals(client, actualClient);
    }

    @Test
    public void loadByUserName_notFoundEmail_throwsUsernameNotFoundException(){
        //given/when/then
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> clientService.loadUserByUsername("example@mail.com"));
    }

    @Test
    public void loadByUserName_nullEmail_throwsIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.loadUserByUsername(null));
    }

    @Test
    public void loadByUserName_emptyEmail_throwsIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.loadUserByUsername(""));
    }

    //    Test for singUpUser method

    @Test
    public void singUpUser_registrationUser_userRegistered(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));


        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        when(passwordEncoder.encode(client.getPassword())).thenReturn(BCryptPassword);
        // when
        clientService.singUpUser(client);
        // then
        final Client expectedClient = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                BCryptPassword,
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));

        verify(clientRepository).save(expectedClient);
    }

    @Test
    public void singUpUser_userAlreadyExist_throwsIllegalStateException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));


        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        //when/then
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_idIsNull_throwsIllegalArgumentException(){
        //given
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(null,
                "Josef",
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_firstNameIsNull_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                null,
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_firstNameIsEmpty_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "",
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_firstNameIsBlank_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "   ",
                "Scott",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_lastNameIsNull_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                null,
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }
    @Test
    public void singUpUser_lastNameIsEmpty_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }
    @Test
    public void singUpUser_lastNameIsBlank_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "    ",
                email,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_emailIsNull_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                null,
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_emailIsEmpty_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "",
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_emailIsBlank_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "      ",
                "password",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }
    @Test
    public void singUpUser_passwordIsNull_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                null,
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_passwordIsEmpty_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                "",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_passwordIsBlank_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                "    ",
                Role.USER,
                false,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_lockedIsNull_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                "password",
                Role.USER,
                null,
                false,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_enabledIsNull_throwsIllegalArgumentException(){
        //given
        final UUID clientId = UUID.fromString("0d4ff2e2-95c5-11ee-b9d1-0242ac120002");
        final UUID cruiseId = UUID.fromString("58075c12-95c5-11ee-b9d1-0242ac120002");
        final String email = "example@mail.com";
        final String BCryptPassword = "$2a$12$4VNgNTGHhGSyOKWILEcsouh1WvJLavJPXWtzecPjKOeoAjzi8v7w6";

        final Client client = new Client(clientId,
                "Josef",
                "Scott",
                "example@mail.com",
                "password",
                Role.USER,
                false,
                null,
                Collections.singletonList(new Cruise(cruiseId,
                        "Warsaw",
                        "Tokyo",
                        new GregorianCalendar(2023, Calendar.DECEMBER, 25, 12, 30).getTime(),
                        BigDecimal.valueOf(10000),
                        "PLN",
                        30)));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(client));
    }

    @Test
    public void singUpUser_clientIsNull_throwsIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.singUpUser(null));
    }

    //    Test for saveNotSingInClient method


    @Test
    public void saveNotSingInClient_clientIsExist_throwsIllegalStateException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(new Client(UUID.randomUUID(), firstName, lastName, email)));

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        lastName,
                        email,
                        Role.USER,
                        null));
    }
    @Test
    public void saveNotSingInClient_firstNameIsNull_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(null,
                        lastName,
                        email,
                        Role.USER,
                        null));
    }
    @Test
    public void saveNotSingInClient_firstNameIsEmpty_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient("",
                        lastName,
                        email,
                        Role.USER,
                        null));
    }

    @Test
    public void saveNotSingInClient_firstNameIsBlank_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient("  ",
                        lastName,
                        email,
                        Role.USER,
                        null));
    }

    @Test
    public void saveNotSingInClient_lastNameIsNull_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        null,
                        email,
                        Role.USER,
                        null));
    }
   @Test
    public void saveNotSingInClient_lastNameIsEmpty_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        "",
                        email,
                        Role.USER,
                        null));
    }
   @Test
    public void saveNotSingInClient_lastNameIsBlank_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        "   ",
                        email,
                        Role.USER,
                        null));
    }
    @Test
    public void saveNotSingInClient_emailIsNull_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        lastName,
                        null,
                        Role.USER,
                        null));
    }

    @Test
    public void saveNotSingInClient_emailIsEmpty_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        lastName,
                        "",
                        Role.USER,
                        null));
    }

    @Test
    public void saveNotSingInClient_emailIsBlank_throwsIllegalArgumentException(){
        //given/when/then
        String firstName = "Josef";
        String lastName = "Scott";
        String email = "example@mail.com";
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.saveNotSingInClient(firstName,
                        lastName,
                        "  ",
                        Role.USER,
                        null));
    }




}
