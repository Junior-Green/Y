package com.y.Y.features.post;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public interface PostService {

    public Post getPostById(UUID postId) throws EntityNotFoundException;

    public void addReply(UUID parentPost, Post reply);

    public void deleteReplyById(UUID postId);

}
