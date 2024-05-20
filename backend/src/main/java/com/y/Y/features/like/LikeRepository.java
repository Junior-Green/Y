package com.y.Y.features.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    @Query("from Like like where like.likedBy.id = ?1")
    List<Like> findLikesByUser(UUID userId);

    @Query("from Like like where like.likedPost.id = ?1")
    List<Like> findLikesByPost(UUID postId);

}
