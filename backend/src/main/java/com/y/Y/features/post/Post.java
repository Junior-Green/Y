package com.y.Y.features.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.y.Y.features.hashtag.HashTag;
import com.y.Y.features.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Post")
public class Post {
    private static final int MAX_POST_LENGTH = 280;

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="author_id", nullable = false, updatable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_post_id", updatable = false)
    private Post parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> replies;

    @Column(nullable = false, length = MAX_POST_LENGTH)
    private String content;

    @Column(nullable = false,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private boolean isQoutePost = false;

    @ManyToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HashTag> hashtags;

    public Post() {}

    public Post(User author, String content, LocalDateTime createdAt) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getParent() {
        return parent;
    }

    public Set<Post> getReplies() {
        return replies;
    }

    public Set<String> getHashtags() {
        return new HashSet<>(hashtags.stream().map(HashTag::getName).toList());
    }

    public void setReplies(Set<Post> replies) {
        this.replies = replies;
    }

    public void addReply(Post reply) {
        if (replies == null) {replies = new HashSet<>();}
        replies.add(reply);
        reply.setParent(this);
        reply.setIsQoutePost(false);
    }

    public UUID getAuthorId(){return author.getId();}

    public boolean isQoutePost() {
        return isQoutePost;
    }

    @JsonIgnore
    public void setIsQoutePost(boolean qoutePost) {
        isQoutePost = qoutePost;
    }

    @JsonIgnore
    public void setAuthor(User author) {
        this.author = author;
    }

    @JsonIgnore
    public void setParent(Post parent) {
        this.parent = parent;
    }

    @JsonIgnore()
    public User getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", author=" + author.getId() +
                '}';
    }
}
