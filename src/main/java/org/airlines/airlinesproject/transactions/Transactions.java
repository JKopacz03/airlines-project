package org.airlines.airlinesproject.transactions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.airlines.airlinesproject.appuser.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Transactions {
    @Id
    private UUID id;
//    private BigDecimal amount;
//    private LocalDateTime dateTime;
//    private
//    private Client client;
}
