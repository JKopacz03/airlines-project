package org.airlines.airlinesproject.paypal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID cruiseId;
    private String firstName;
    private String lastName;
    private String email;
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

}
