package com.y.Y.features.hashtag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.y.Y.features.post.Post;
import jakarta.persistence.*;

import java.util.Set;

@Table(name = "HashTag")
@Entity
public class HashTag {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "hashtag_posts",
            joinColumns = @JoinColumn(name = "name"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts;

    public HashTag() {}

    public HashTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
