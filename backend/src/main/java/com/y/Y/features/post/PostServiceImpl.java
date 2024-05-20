package com.y.Y.features.post;

import com.y.Y.error.custom_exceptions.BadRequestException;
import com.y.Y.error.custom_exceptions.DuplicateDataException;
import com.y.Y.features.hashtag.HashTag;
import com.y.Y.features.hashtag.HashTagRepository;
import com.y.Y.features.like.Like;
import com.y.Y.features.like.LikeRepository;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static com.y.Y.utils.UtilityService.extractHashtagNames;
import static com.y.Y.utils.UtilityService.hasHashtag;


@Service
public class PostServiceImpl implements PostService{

    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final LikeRepository likeRepository;


    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, HashTagRepository hashTagRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.likeRepository = likeRepository;
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
