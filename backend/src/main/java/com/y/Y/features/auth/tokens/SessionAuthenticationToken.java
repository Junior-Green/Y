package com.y.Y.features.auth.tokens;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SessionAuthenticationToken implements Authentication {
    private final UUID sessionId;
    private final Collection<? extends GrantedAuthority> grantedAuthorities;

    public SessionAuthenticationToken(UUID sessionId, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.sessionId = sessionId;
        this.grantedAuthorities = grantedAuthorities;
    }

    public SessionAuthenticationToken(UUID sessionId) {
        this.sessionId = sessionId;
        this.grantedAuthorities = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Object getCredentials() {
        return sessionId;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return sessionId;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    /**
     * Compares this {@code Principal} to the specified object.
     * Returns {@code true}
     * if the object passed in matches the {@code Principal} represented by
     * the implementation of this interface.
     *
     * @param another {@code Principal} to compare with.
     * @return {@code true} if the {@code Principal} passed in is the same as
     * that encapsulated by this {@code Principal}, and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object another) {
        if (UUID.class != another.getClass()) return false;
        return sessionId.equals(another);
    }


    @Override
    public String toString() {
        return "SessionAuthenticationToken{" +
                "sessionId=" + sessionId +
                '}';
    }

    /**
     * Returns the name of this {@code Principal}.
     *
     * @return the name of this {@code Principal}.
     */
    @Override
    public String getName() {
        return "Session ID";
    }

    /**
     * Returns {@code true} if the specified subject is implied by this
     * {@code Principal}.
     *
     * @param subject the {@code Subject}
     * @return {@code true} if {@code subject} is non-null and is
     * implied by this {@code Principal}, or false otherwise.
     * @implSpec The default implementation of this method returns {@code true} if
     * {@code subject} is non-null and contains at least one
     * {@code Principal} that is equal to this {@code Principal}.
     *
     * <p>Subclasses may override this with a different implementation, if
     * necessary.
     * @since 1.8
     */
    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
