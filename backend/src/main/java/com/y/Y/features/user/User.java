package com.y.Y.features.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.features.auth.Auth;
import com.y.Y.features.user.user_details.CustomUserDetails;
import com.y.Y.features.user.user_profile.UserProfile;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "\"User\"")
public class User implements CustomUserDetails{

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(updatable = false, nullable = false)
    private LocalDateTime accountCreation;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private boolean isVerified = false;

    @Column(nullable = false)
    private String displayName;

    private String websiteUrl;
    private String location;
    private String gender;
    private String bio;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Auth auth;

    @ManyToMany
    @JoinTable(
            name = "blocked_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "blocked_user_id")
    )
    private Set<User> blockedUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "following",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following = new HashSet<>();

    public User() {}

    public User(String username,
                String firstName,
                String middleName,
                String lastName,
                String email,
                LocalDate birthday,
                String phoneNumber,
                String gender,
                String bio) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.accountCreation = LocalDateTime.now();
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.bio = bio;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getAccountCreation() {
        return accountCreation;
    }

    public void setAccountCreation(LocalDateTime accountCreation) {
        this.accountCreation = accountCreation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public Set<UUID> getFollowersIds() {
        return new HashSet<>(getFollowers().stream().map(User::getId).toList());
    }

    public Set<UUID> getFollowingIds() {
        return new HashSet<>(getFollowing().stream().map(User::getId).toList());
    }

    public Set<UUID> getBlockedUsersIds() {
        return new HashSet<>(getBlockedUsers().stream().map(User::getId).toList());
    }

    public void setWebsiteUrl(String url) {
        this.websiteUrl = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getBio() {
        return bio == null ? "" : bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void followUser(User user) {
        if (following == null) {following = new HashSet<>();}
        if (following.contains(user)) throw new DuplicateDataException(HttpStatus.BAD_REQUEST, DuplicateDataException.DataType.USER);

        following.add(user);
    }

    public void unfollowUser(User user){
        if(following == null || following.isEmpty()) return;
        if(!following.contains(user)) throw new NoSuchElementException("Cannot unfollow user. User: " + user.getId() + " is not being followed.");
        following.remove(user);
    }

    public void addFollower(User newFollower) {
        if (followers == null) {followers = new HashSet<>();}
        if (followers.contains(newFollower)) throw new DuplicateDataException(HttpStatus.BAD_REQUEST, DuplicateDataException.DataType.USER);
        followers.add(newFollower);
    }

    public void removeFollower(User follower){
        if(followers == null || followers.isEmpty()) return;
        if(!followers.contains(follower)) throw new NoSuchElementException("Cannot remove follower. User: " + follower.getId() + " is not following.");

        followers.remove(follower);
    }

    public void blockUser(User user) {
        if (blockedUsers == null) {blockedUsers = new HashSet<>();}
        if (blockedUsers.contains(user)) throw new DuplicateDataException(HttpStatus.BAD_REQUEST, DuplicateDataException.DataType.USER);

        blockedUsers.add(user);
    }

    public void unblockUser(User user) {
        if(blockedUsers == null || blockedUsers.isEmpty()) return;
        if(!blockedUsers.contains(user)) throw new NoSuchElementException("Cannot unblock user. User: " + user.getId() + " is not blocked.");
        blockedUsers.remove(user);
    }

    @JsonIgnore
    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    @JsonIgnore
    public Set<User> getBlockedUsers() {
        return blockedUsers;
    }

    @JsonIgnore
    public void setBlockedUsers(Set<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    @JsonIgnore
    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    @JsonIgnore
    public Set<User> getFollowers() {
        return followers;
    }

    @JsonIgnore
    public Set<User> getFollowing() {
        return following;
    }

    @JsonIgnore
    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @JsonIgnore
    public String getPassword(){
        return auth.getPassword();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", accountCreation=" + accountCreation +
                '}';
    }
}
