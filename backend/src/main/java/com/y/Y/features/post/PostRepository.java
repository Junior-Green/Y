package com.y.Y.features.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("from Post p where p.author.id = ?1")
    public List<Post> findAllPostsByUser(UUID userId);

}
