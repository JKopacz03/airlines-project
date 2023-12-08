package org.airlines.airlinesproject;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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




}
