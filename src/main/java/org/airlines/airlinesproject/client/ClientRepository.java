package org.airlines.airlinesproject.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Client a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Client a SET a.password = ?2 WHERE a.email = ?1")
    void modifyUserPassword(String email, String newEncodedPassword);

}
