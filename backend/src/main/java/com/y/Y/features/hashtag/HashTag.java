package com.y.Y.features.hashtag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.y.Y.features.post.Post;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "HashTag")
@Entity
public class HashTag {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String hashtag;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "hashtag_posts",
            joinColumns = @JoinColumn(name = "hashtag"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts;

    public HashTag() {}

    public HashTag(String name) {
        this.hashtag = name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post){
        if (posts == null) {posts = new HashSet<>();}
        posts.add(post);
    }

    @JsonIgnore
    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    @JsonIgnore
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
