package com.y.Y.features.likes;


import com.y.Y.features.post.Post;
import com.y.Y.features.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Likes")
public class Likes {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(nullable = false ,updatable = false)
    private User likedBy;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(nullable = false, updatable = false)
    private Post likedPost;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;


}
