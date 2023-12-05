package org.airlines.airlinesproject.cruises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CruiseRepository extends JpaRepository<Cruise, UUID> {

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE Cruise a SET a.number_of_available_seats = ?2 WHERE a.id = ?1")
//    void modifyNumberOfAvailableSeats(UUID idOfCruise, int newAmountOfAvailableSeats);

}
