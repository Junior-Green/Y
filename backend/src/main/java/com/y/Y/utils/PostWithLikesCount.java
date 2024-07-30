package com.y.Y.utils;

import com.y.Y.features.post.Post;

public final class PostWithLikesCount {
    private final Long bCount;
    private final Post p;

    public Long getbCount() {
        return bCount;
    }

    public Post getP() {
        return p;
    }

    public PostWithLikesCount(Long bCount, Post p){
        this.bCount = bCount;
        this.p = p;
    }
}
