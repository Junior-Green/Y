package com.y.Y.features.hashtag;

import com.y.Y.features.post.Post;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class HashTagServiceImpl implements HashTagService{

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagServiceImpl(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    @Override
    public Set<Post> getPostsByHashtag(String hashtag) {
        Optional<HashTag> hashTag = hashTagRepository.findById(hashtag);
        return hashTag.map(tag -> new HashSet<>(tag.getPosts())).orElseGet(HashSet::new);
    }
}
