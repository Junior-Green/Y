package com.y.Y.features.auth;


import com.y.Y.features.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "Auth")
@Table(name = "Auth")
public class Auth {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true, updatable = false)
    private User user;

    private String password;

    @Column(nullable = false)
    private String salt;

    public Auth() {}

    public Auth(User user, String password, String salt) {
        this.user = user;
        this.password = password;
        this.salt = salt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
