package com.y.Y.features.post.controller_requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.y.Y.features.post.Post;

public class CreatePostRequest {
    @JsonProperty("post")
    private final Post post;


    public CreatePostRequest(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
