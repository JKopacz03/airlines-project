package org.airlines.airlinesproject.client;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.authenticationAndRegistration.token.ConfirmationToken;
import org.airlines.airlinesproject.authenticationAndRegistration.token.ConfirmationTokenService;
import org.airlines.airlinesproject.client.dto.ClientNewPasswordRequest;
import org.airlines.airlinesproject.client.dto.ClientPlaceOrderRequest;
import org.airlines.airlinesproject.client.dto.ClientResponse;
import org.airlines.airlinesproject.cruises.Cruise;
import org.airlines.airlinesproject.cruises.CruiseService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthenticationManager authenticationManager;
    private final CruiseService cruiseService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public void saveNotSingInClient(String firstName, String lastName, String email, Role user, ArrayList<Cruise> cruises) {
        final Client client = new Client(
                UUID.randomUUID(),
                firstName,
                lastName,
                email,
                user,
                cruises
        );

        clientRepository.save(client);
    }

    public String signUpUser(Client client) {
        final boolean userExists = clientRepository
                .findByEmail(client.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        final String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);

        clientRepository.save(client);

        final String token = UUID.randomUUID().toString();


        final ConfirmationToken confirmationToken = new ConfirmationToken(
                UUID.randomUUID(),
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                client
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return clientRepository.enableAppUser(email);
    }

    public void modifyPassword(ClientNewPasswordRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getCurrentPassword()
                )
        );

        final String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        clientRepository.modifyUserPassword(request.getEmail(), encodedPassword);
    }

    public void saveCruiseToClient(ClientPlaceOrderRequest clientPlaceOrderRequest) {

        //        Save cruise to client

        if(clientRepository.findByEmail(clientPlaceOrderRequest.getEmail()).isEmpty()){
            saveNotSingInClient(
                    clientPlaceOrderRequest.getFirstName(),
                    clientPlaceOrderRequest.getLastName(),
                    clientPlaceOrderRequest.getEmail(),
                    Role.USER,
                    new ArrayList<Cruise>()
            );
        }

        final Client client = clientRepository.findByEmail(clientPlaceOrderRequest.getEmail())
                .orElseThrow();

        final Cruise cruise = cruiseService.findById(clientPlaceOrderRequest.getCruiseId());

        if(client.getCruises().isEmpty()){
            final ArrayList<Cruise> cruises = new ArrayList<>();
            cruises.add(cruise);

            client.setCruises(cruises);
            clientRepository.save(client);
        }

        if(!client.getCruises().isEmpty()){
            final List<Cruise> clientCruises = client.getCruises();
            clientCruises.add(cruise);

            client.setCruises(clientCruises);

            clientRepository.save(client);
        }

        //        Save client to cruise

        cruiseService.saveClientToCruise(clientPlaceOrderRequest.getCruiseId(), client);
    }

    public List<ClientResponse> findAll(){
        final List<Client> clients = clientRepository.findAll();

        return clients.stream()
                .map(this::mapClientToClientResponse)
                .toList();

    }

    public ClientResponse findByEmail(String email){
        final Client client = clientRepository.findByEmail(email).orElseThrow();

        return mapClientToClientResponse(client);
    }

    private ClientResponse mapClientToClientResponse(Client client){

        return ClientResponse.builder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .cruises(client.getCruises())
                .build();
    }
}
