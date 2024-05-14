package com.y.Y.features.post;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface PostService {

    public Post getPostById(UUID postId) throws EntityNotFoundException;

    public Post addReply(UUID authorId, UUID parentPostId, Post reply);

    public List<Post> getPostsByUser(UUID userId);

    public Post createPost(UUID author, Post post);

    @Transactional
    Post createQoutePost(UUID authorId, UUID qoutedPostId, Post qoute);

    public void deletePostById(UUID postId);

}
