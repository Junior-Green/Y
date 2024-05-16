package com.y.Y.features.hashtag;

import com.y.Y.features.post.Post;

import java.util.Set;

public interface HashTagService {

    public Set<Post> getPostsByHashtag(String name);
}
