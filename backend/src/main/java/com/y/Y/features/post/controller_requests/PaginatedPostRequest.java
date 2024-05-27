package com.y.Y.features.post.controller_requests;

import com.y.Y.features.post.Post;

import java.util.List;

public final class PaginatedPostRequest {

    private List<Post> posts;
    private Integer nextPage;
    private Integer previousPage;

    public PaginatedPostRequest() {
    }

    public PaginatedPostRequest(List<Post> posts, Integer nextPage, Integer previousPage) {
        this.posts = posts;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public void setPreviousPage(Integer previousPage) {
        if(previousPage != null && previousPage <= 0 ) {
            this.previousPage = 1;
        }
        else {
            this.previousPage = previousPage;
        }
    }
}
