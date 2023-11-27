package org.airlines.airlinesproject.registration;

import lombok.AllArgsConstructor;
import org.airlines.airlinesproject.appuser.AppUser;
import org.airlines.airlinesproject.appuser.AppUserRole;
import org.airlines.airlinesproject.appuser.AppUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    public String register(RegistrationRequest request) {
        final boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid: " + request.getEmail());
        }
        return appUserService.signUpUser(
                new AppUser(
                        UUID.randomUUID(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
