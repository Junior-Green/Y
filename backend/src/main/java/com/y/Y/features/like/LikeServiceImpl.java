package com.y.Y.features.like;

import com.y.Y.features.post.Post;
import com.y.Y.features.post.PostRepository;
import com.y.Y.features.user.User;
import com.y.Y.features.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Like> getLikesByUserId(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        return likeRepository.findLikesByUser(user.orElseThrow().getId());
    }

    @Override
    public List<Like> getLikesByPostId(UUID postId) {
        Optional<Post> post = postRepository.findById(postId);
        return likeRepository.findLikesByPost(post.orElseThrow().getId());
    }
}
