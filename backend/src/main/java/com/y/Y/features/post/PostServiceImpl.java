package com.y.Y.features.post;

import com.y.Y.error.custom_exceptions.BadRequestException;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.features.hashtag.HashTag;
import com.y.Y.features.hashtag.HashTagRepository;
import com.y.Y.features.like.Like;
import com.y.Y.features.like.LikeRepository;
import com.y.Y.features.post.controller_requests.PaginatedPostRequest;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import com.y.Y.utils.PostWithLikesCount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.y.Y.utils.UtilityService.extractHashtagNames;
import static com.y.Y.utils.UtilityService.hasHashtag;


@Service
public class PostServiceImpl implements PostService{

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    private static final int PAGE_SIZE = 10;
    private static final int MAX_PAGES = 15;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final LikeRepository likeRepository;
    private final EntityManager entityManager;


    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, HashTagRepository hashTagRepository, LikeRepository likeRepository, EntityManager entityManager) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.likeRepository = likeRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Post getPostById(UUID postId) throws NoSuchElementException {
        return postRepository.findById(postId).orElseThrow();
    }

    @Override
    @Transactional
    public Post addReply(UUID authorId, UUID parentPostId,  String content) {

        Optional<User> authorOptional = userRepository.findById(authorId);

        if(authorOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + authorId + " cannot be found");
        }

        Post reply = new Post();
        Post parent = getPostById(parentPostId);

        reply.setAuthor(authorOptional.get());
        reply.setCreatedAt(LocalDateTime.now());
        reply.setContent(content);
        reply.setParent(parent);
        reply.setIsQoutePost(false);

        createHashtags(reply);

        return postRepository.save(reply);
    }

    @Override
    public List<Post> getPostsByUser(UUID userId) {
        return postRepository.findAllPostsByUser(userId);
    }

    @Override
    @Transactional
    public Post createPost(UUID authorId, String content){
        Optional<User> authorOptional = userRepository.findById(authorId);

        if(authorOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + authorId + " cannot be found");
        }

        Post post = new Post();

        post.setContent(content);
        post.setAuthor(authorOptional.get());
        post.setParent(null);
        post.setIsQoutePost(false);
        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        createHashtags(savedPost);

        return savedPost;
    }

    @Override
    @Transactional
    public Post createQoutePost(UUID authorId, UUID qoutedPostId,  String content) {
        Optional<User> authorOptional = userRepository.findById(authorId);
        if(authorOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + authorId + " doest not exist");
        }

        Optional<Post> qoutedPost = postRepository.findById(qoutedPostId);
        if(qoutedPost.isEmpty()){
            throw new EntityNotFoundException("Post: " + qoutedPostId + " does not exist");
        }

        Post qoute = qoutedPost.get();
        qoute.setAuthor(authorOptional.get());
        qoute.setParent(qoutedPost.get());
        qoute.setIsQoutePost(true);
        qoute.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(qoute);
        createHashtags(savedPost);

        return savedPost;
    }

    @Override
    public void deletePostById(UUID postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public void likePost(UUID likerId, UUID postId) {
        Optional<User> userOptional = userRepository.findById(likerId);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + likerId + " doest not exist");
        }

        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new EntityNotFoundException("Post: " + postId + " does not exist");
        }

        if(post.get().getLikes().stream().anyMatch(like -> like.getLikedByUserId() == likerId)){
            throw new DuplicateDataException(HttpStatus.CONFLICT, DuplicateDataException.DataType.USER);
        }

        Like like = new Like(userOptional.get(), post.get());
        like.setCreated(LocalDateTime.now());

        likeRepository.save(like);
    }

    @Override
    @Transactional
    public void unlikePost(UUID unlikerId, UUID postId) {
        Optional<User> userOptional = userRepository.findById(unlikerId);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User: " + unlikerId + " doest not exist");
        }

        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()){
            throw new EntityNotFoundException("Post: " + postId + " does not exist");
        }

        for (Like like : post.get().getLikes()){
            if(like.getLikedByUserId().equals(unlikerId)){
                likeRepository.delete(like);
                post.get().removeLike(like);
                postRepository.save(post.get());
                return;
            }
        }

        throw new EntityNotFoundException("User: " + unlikerId + " did not like post: " + postId);
    }

    @Override
    @Transactional
    public PaginatedPostRequest getPaginatedPosts(int pageNumber) {
        PageRequest pageRequest =  PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<Post> page = postRepository.getPaginatedPosts(pageRequest);

        PaginatedPostRequest req = new PaginatedPostRequest();
        if(pageNumber == MAX_PAGES || !page.hasNext()){
            req.setNextPage(null);
        }
        else {
            req.setNextPage(pageNumber + 1);
        }

        if(pageNumber - 1 < 1 || !page.hasPrevious()){
            req.setPreviousPage(null);
        }
        else{
            req.setPreviousPage(pageNumber - 1);
        }

        req.setPosts(page.stream().toList());
        return req;
    }

    @Override
    @Transactional
    public PaginatedPostRequest getPaginatedFollowersPosts(UUID userId, int pageNumber) {
       Optional<User> userOptional = userRepository.findById(userId);
       if(userOptional.isEmpty()){
           throw new EntityNotFoundException("User: " + userId + " does not exist");
       }

       List<UUID> followerIds = userOptional.get().getFollowersIds().stream().toList();

       PageRequest pageRequest =  PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(Sort.Direction.DESC,"createdAt"));
       Page<Post> page = postRepository.getPaginatedFollowersPosts(pageRequest,followerIds);

        PaginatedPostRequest req = new PaginatedPostRequest();
        if(pageNumber == MAX_PAGES || !page.hasNext()){
            req.setNextPage(null);
        }
        else {
            req.setNextPage(pageNumber + 1);
        }

        if(pageNumber - 1 < 1 || !page.hasPrevious()){
            req.setPreviousPage(null);
        }
        else{
            req.setPreviousPage(pageNumber - 1);
        }


        req.setPosts(page.getContent());
        return req;
    }

    @Override
    public PaginatedPostRequest getPaginatedPopularPosts(int pageNumber) {
        PageRequest pageRequest =  PageRequest.of(pageNumber,PAGE_SIZE);
        Page<PostWithLikesCount> page = postRepository.findAllWithLikesCountOrderByCountDesc(pageRequest);

        PaginatedPostRequest req = new PaginatedPostRequest();
        if(pageNumber == MAX_PAGES || !page.hasNext()){
            req.setNextPage(null);
        }
        else {
            req.setNextPage(pageNumber + 1);
        }

        if(pageNumber - 1 < 1 || !page.hasPrevious()){
            req.setPreviousPage(null);
        }
        else{
            req.setPreviousPage(pageNumber - 1);
        }


        req.setPosts(page.getContent().stream().map(PostWithLikesCount::getP).toList());
        return req;
    }

    @Transactional
    private void createHashtags(Post post){
        if(hasHashtag(post.getContent())){
            List<String> hashtags = extractHashtagNames(post.getContent()).stream().toList();

            for (String hashtag : hashtags){
                Optional<HashTag> hashTagOptional = hashTagRepository.findById(hashtag);

                if(hashTagOptional.isPresent()){
                    HashTag hashtagFetched = hashTagOptional.get();
                    hashtagFetched.addPost(post);
                    hashTagRepository.save(hashtagFetched);
                }
                else{
                    HashTag newHashtag = new HashTag(hashtag);
                    newHashtag.addPost(post);
                    hashTagRepository.save(newHashtag);
                }
            }
        }
    }
}
