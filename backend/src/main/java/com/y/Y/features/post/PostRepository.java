package com.y.Y.features.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("SELECT * FROM Post WHERE author.id = ?1")
    public List<Post> findAllPostsByUser(UUID userId);

}
