package com.y.Y.features.user.user_details;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface CustomUserDetails extends UserDetails {

    public UUID getId();
}
