package org.airlines.airlinesproject.client;

import lombok.RequiredArgsConstructor;
import org.airlines.airlinesproject.authenticationAndRegistration.token.ConfirmationToken;
import org.airlines.airlinesproject.authenticationAndRegistration.token.ConfirmationTokenService;
import org.airlines.airlinesproject.client.dto.ClientNewPasswordRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
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
}
