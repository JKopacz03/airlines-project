package org.airlines.airlinesproject.registration.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.airlines.airlinesproject.appuser.Client;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    private UUID id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime localDateTimeAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"

    )
    private Client client;

    public ConfirmationToken(UUID id,
                             String token,
                             LocalDateTime localDateTimeAt,
                             LocalDateTime expiresAt,
                             Client client) {
        this.id = id;
        this.token = token;
        this.localDateTimeAt = localDateTimeAt;
        this.expiresAt = expiresAt;
        this.client = client;
    }
}
