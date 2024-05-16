package com.y.Y.features.hashtag;

import com.y.Y.features.post.Post;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class HashTagServiceImpl implements HashTagService{

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public Set<Post> getPostsByHashtag(String hashtag) {
        HashTag hashTag = hashTagRepository.findByHashtagName(hashtag);
        if (hashTag == null) {
            throw new EntityNotFoundException("hashtag '" + hashtag + "' does not exist.");
        }
        return new HashSet<>(hashTag.getPosts());
    }
}
