package com.y.Y.features.session;

import com.y.Y.features.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "session")
@Entity
public class Session {
    public static final int SESSION_EXPIRATION_SECONDS = 2592000;

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiration;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", nullable = false, updatable = false)
    private User user;

    public Session(){}

    public Session(LocalDateTime expiration, User user) {
        this.expiration = expiration;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public User getUser() {
        return user;
    }


}
