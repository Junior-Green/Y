package com.y.Y.features.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("from Post p where p.author.id = ?1")
    List<Post> findAllPostsByUser(UUID userId);

    @Query("select p from Post p where p.parent is null")
    Page<Post> getPaginatedPosts(Pageable pageable);

    @Query("from Post p where p.author.id in :ids and p.parent is null")
    Page<Post> getPaginatedFollowersPosts(Pageable pageable, @Param("ids") Collection<UUID> followerIds);
}
