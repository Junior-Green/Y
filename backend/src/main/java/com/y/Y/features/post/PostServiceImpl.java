package com.y.Y.features.post;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostServiceImpl implements PostService{

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    public final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post getPostById(UUID postId) throws EntityNotFoundException {
        return postRepository.getReferenceById(postId);
    }

    @Override
    @Transactional
    public void addReply(UUID parentId, Post reply) {
        Post parentPost = postRepository.getReferenceById(parentId);

        reply.setParent(parentPost);
        reply.setCreatedAt(LocalDateTime.now());
        postRepository.save(reply);
    }

    @Override
    public void deleteReplyById(UUID postId) {
        postRepository.deleteById(postId);
    }
}
