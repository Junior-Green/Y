package com.y.Y.features.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.features.like.Like;
import com.y.Y.features.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "Post")
public class Post {
    private static final int MAX_POST_LENGTH = 280;

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="author_id", nullable = false, updatable = false)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_post_id", updatable = false)
    private Post parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Post> replies;

    @Column(nullable = false, length = MAX_POST_LENGTH)
    private String content;

    @Column(nullable = false,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private boolean isQoutePost = false;

    @OneToMany(mappedBy = "likedPost",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Like> likes;

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

    public UUID getParentId() {return parent == null ? null : parent.id ;}

    public List<UUID> getReplyIds() {return replies.stream().map(Post::getId).toList();}

    public void setReplies(Set<Post> replies) {
        this.replies = replies;
    }

    public void addReply(Post reply) {
        if (replies == null) {replies = new HashSet<>();}
        reply.setParent(this);
        reply.setIsQoutePost(false);
        replies.add(reply);
    }

    public void addLike(Like like) {
        if (likes == null) {likes = new HashSet<>();}
        if(likes.stream().noneMatch(l -> l.getLikedByUserId() == like.getLikedByUserId())) {
            likes.add(like);
            return;
        }
        throw new DuplicateDataException(HttpStatus.BAD_REQUEST, DuplicateDataException.DataType.USER);
    }

    public void removeLike(Like like) {
        if (likes == null) {likes = new HashSet<>();}
        if(!likes.contains(like)){
            throw new NoSuchElementException("This post is not liked by user: " + like.getLikedPostId());
        }
        likes.remove(like);
    }

    public UUID getAuthorId(){return author.getId();}

    public boolean isQoutePost() {
        return isQoutePost;
    }

    public Set<Like> getLikes(){return likes;}

    @JsonIgnore
    public Set<Post> getReplies() {
        return replies;
    }

    @JsonIgnore
    public Post getParent() {
        return parent;
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
