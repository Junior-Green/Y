package com.y.Y.features.user.user_profile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface UserProfile {


    public UUID getId();

    public String getLocation();

    public LocalDate getBirthday();

    public LocalDate getAccountCreation();

    public String getBio();

    public String getWebsiteUrl();

    public boolean isVerified();

}
