package org.airlines.airlinesproject.cruises;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CruiseRepository extends JpaRepository<Cruise, UUID> {



}
