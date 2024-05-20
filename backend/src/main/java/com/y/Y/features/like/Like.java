package com.y.Y.features.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.y.Y.features.post.Post;
import com.y.Y.features.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Likes")
public class Like {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User likedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, updatable = false)
    private Post likedPost;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    public Like() {
    }

    public Like(User likedBy, Post likedPost) {
        this.likedBy = likedBy;
        this.likedPost = likedPost;
    }

    public UUID getLikedByUserId(){
        return likedBy.getId();
    }

    public void setLikedBy(User likedBy) {
        this.likedBy = likedBy;
    }

    public UUID getLikedPostId() {return likedPost.getId();}

    public void setLikedPost(Post likedPost) {
        this.likedPost = likedPost;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @JsonIgnore
    public Post getLikedPost() {
        return likedPost;
    }

    @JsonIgnore
    public User getLikedBy() {
        return likedBy;
    }

    @JsonIgnore
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
