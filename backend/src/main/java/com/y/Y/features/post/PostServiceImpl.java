package com.y.Y.features.post;

import com.y.Y.error.custom_exceptions.BadRequestException;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PostServiceImpl implements PostService{

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    public final PostRepository postRepository;
    public final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post getPostById(UUID postId) throws EntityNotFoundException {
        return postRepository.getReferenceById(postId);
    }

    @Override
    @Transactional
    public Post addReply(UUID authorId, UUID parentPostId, Post reply) {
        if(reply.getContent() == null){
            throw new BadRequestException("Content not provided",HttpStatus.BAD_REQUEST);
        }

        User author = userRepository.getReferenceById(authorId);

        reply.setAuthor(author);
        reply.setCreatedAt(LocalDateTime.now());

        Post parent = getPostById(parentPostId);
        parent.addReply(reply);

        postRepository.save(parent);
        return postRepository.save(reply);
    }

    @Override
    public List<Post> getPostsByUser(UUID userId) {
        return postRepository.findAllPostsByUser(userId);
    }

    @Override
    @Transactional
    public Post createPost(UUID authorId, Post post) {
        if(post.getContent() == null){
            throw new BadRequestException("Content not provided",HttpStatus.BAD_REQUEST);
        }

        User author = userRepository.getReferenceById(authorId);

        post.setAuthor(author);
        post.setParent(null);
        post.setIsQoutePost(false);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post createQoutePost(UUID authorId, UUID qoutedPostId, Post qoute) {
        if(qoute.getContent() == null){
            throw new BadRequestException("Content not provided",HttpStatus.BAD_REQUEST);
        }

        User author = userRepository.getReferenceById(authorId);
        Post qoutedPost = postRepository.getReferenceById(qoutedPostId);

        qoute.setAuthor(author);
        qoute.setParent(qoutedPost);
        qoute.setIsQoutePost(true);
        qoute.setCreatedAt(LocalDateTime.now());
        return postRepository.save(qoute);
    }

    @Override
    public void deletePostById(UUID postId) {
        postRepository.deleteById(postId);
    }
}
