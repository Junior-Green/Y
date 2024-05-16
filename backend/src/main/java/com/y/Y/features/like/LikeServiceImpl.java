package com.y.Y.features.like;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public List<Like> getLikesByUserId(UUID userId) {
        return likeRepository.findLikesByUser(userId);
    }

    @Override
    public List<Like> getLikesByPostId(UUID postId) {
        return likeRepository.findLikesByPost(postId);
    }
}
