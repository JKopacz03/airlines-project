package org.airlines.airlinesproject.transactions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

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
