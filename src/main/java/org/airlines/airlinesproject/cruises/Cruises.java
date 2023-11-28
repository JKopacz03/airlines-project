package org.airlines.airlinesproject.cruises;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.airlines.airlinesproject.appuser.Client;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Cruises {

    @Id
    private UUID Id;
    private String cruiseFrom;
    private String cruiseTo;
    private Date dateOfStartCruise;
    private BigDecimal standardPrice;
    @ManyToMany
    private List<Client> clients;

}
