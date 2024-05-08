package com.y.Y.features.user.user_details;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    public String getSalt();
}
