package com.y.Y.features.post;

import com.y.Y.features.post.controller_requests.PaginatedPostRequest;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public interface PostService {

    public Post getPostById(UUID postId) throws EntityNotFoundException;

    public Post addReply(UUID authorId, UUID parentPostId, String content);

    public List<Post> getPostsByUser(UUID userId);

    public Post createPost(UUID author, String content);

    Post createQoutePost(UUID authorId, UUID qoutedPostId, String content);

    public void deletePostById(UUID postId);

    void likePost(UUID likerId, UUID postId);

    void unlikePost(UUID unlikerId, UUID postId);

    PaginatedPostRequest getPaginatedPosts(int pageNumber);

    PaginatedPostRequest getPaginatedFollowersPosts(UUID userId, int pageNumber);
}
