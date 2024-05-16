package com.y.Y.features.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<UUID, Like> {

    @Query("SELECT * FROM like WHERE likedBy.id = ?1")
    List<Like> findLikesByUser(UUID userId);

    @Query("SELECT * FROM like WHERE likedPost.id = ?1")
    List<Like> findLikesByPost(UUID postId);

}
