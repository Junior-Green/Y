package com.y.Y.features.post;

import com.y.Y.error.custom_exceptions.BadRequestException;
import com.y.Y.features.hashtag.HashTagService;
import com.y.Y.features.like.Like;
import com.y.Y.features.like.LikeService;
import com.y.Y.features.post.controller_requests.PaginatedPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "http://localhost:5173")
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

    @GetMapping
    public ResponseEntity<PaginatedPostRequest> getPaginatedPosts(@RequestParam("page") int pageNumber){
        if(pageNumber < 0) throw new BadRequestException("request param (page) must be greater than 0", HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(postService.getPaginatedPosts(pageNumber));
    }

    @GetMapping("/followers")
    public ResponseEntity<PaginatedPostRequest> getPaginatedFollowersPosts(@RequestParam("page") int pageNumber){
        if(pageNumber < 0) throw new BadRequestException("request param (page) must be greater than 0", HttpStatus.BAD_REQUEST);

        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(postService.getPaginatedFollowersPosts((UUID) authenticatedUser.getPrincipal(),pageNumber));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(value = "id") UUID postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping(path = "/me")
    public ResponseEntity<List<Post>> getAllPostsFromAuthenticatedUser(){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(postService.getPostsByUser((UUID) authenticatedUser.getPrincipal()));
    }

    @GetMapping("/likes/{id}")
    public ResponseEntity<List<Like>> getAllPostLikes(@PathVariable("id") UUID postId){
        return ResponseEntity.ok(likeService.getLikesByPostId(postId));
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<List<Post>> getPostsByHashtag(@PathVariable("hashtag") String hashtag){
        return ResponseEntity.ok(hashTagService.getPostsByHashtag(hashtag).stream().toList());
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody String content, @RequestParam(value = "qoutedId", required = false) UUID qoutedPostId){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();

        if(qoutedPostId != null){
            return ResponseEntity.ok(postService.createQoutePost((UUID) authenticatedUser.getPrincipal(), qoutedPostId, content));
        }

        return ResponseEntity.ok(postService.createPost((UUID) authenticatedUser.getPrincipal(), content));
    }

    @PostMapping(path = "/like/{id}")
    public ResponseEntity<HttpStatus> likePost(@PathVariable("id") UUID postId){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        postService.likePost((UUID) authenticatedUser.getPrincipal(), postId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<Post> createReply(@PathVariable("id") UUID parentPostId, @RequestBody String content){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(postService.addReply((UUID) authenticatedUser.getPrincipal(),parentPostId, content));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable UUID postId){
        postService.deletePostById(postId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/like/{id}")
    public ResponseEntity<HttpStatus> unlikePost(@PathVariable("id") UUID postId){
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        postService.unlikePost((UUID) authenticatedUser.getPrincipal(), postId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
