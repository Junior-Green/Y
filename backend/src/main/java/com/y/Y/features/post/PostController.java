package com.y.Y.features.post;

import com.y.Y.features.hashtag.HashTagService;
import com.y.Y.features.like.Like;
import com.y.Y.features.like.LikeService;
import com.y.Y.features.post.controller_requests.CreatePostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final HashTagService hashTagService;

    @Autowired
    public PostController(PostService postService, LikeService likeService, HashTagService hashTagService) {
        this.postService = postService;
        this.likeService = likeService;
        this.hashTagService = hashTagService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable UUID postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<Post>> getAllPostsFromAuthenticatedUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(postService.getPostsByUser((UUID) authenticatedUser.getPrincipal()));
    }

    @GetMapping("/likes/{id}")
    public ResponseEntity<List<Like>> getAllPostLikes(@PathVariable("id") UUID id){
        return ResponseEntity.ok(likeService.getLikesByPostId(id));
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<List<Post>> getPostsByHashtag(@PathVariable("hashtag") String hashtag){
        return ResponseEntity.ok(hashTagService.getPostsByHashtag(hashtag).stream().toList());
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request, @RequestParam(value = "qoutedId", required = false) UUID qoutedPostId){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        if(qoutedPostId != null){
            return ResponseEntity.ok(postService.createQoutePost((UUID) authenticatedUser.getPrincipal(), qoutedPostId, request.getPost()));
        }

        return ResponseEntity.ok(postService.createPost((UUID) authenticatedUser.getPrincipal(),request.getPost()));
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<Post> createReply(@PathVariable UUID parentPostId, @RequestBody CreatePostRequest request){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(postService.addReply((UUID) authenticatedUser.getPrincipal(),parentPostId,request.getPost()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deletePost(@PathVariable UUID postId){
        postService.deletePostById(postId);
        return ResponseEntity.ok("Post successfully deleted.");
    }

}
