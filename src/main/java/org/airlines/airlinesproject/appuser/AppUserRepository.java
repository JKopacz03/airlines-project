package org.airlines.airlinesproject.appuser;

import org.airlines.airlinesproject.appuser.AppUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<UUID, AppUser> {

    Optional<AppUser> findByEmail(String email);
}
