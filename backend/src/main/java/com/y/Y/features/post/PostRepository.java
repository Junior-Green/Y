package com.y.Y.features.post;

import com.y.Y.utils.PostWithLikesCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Query(
            value = "select new package.PostWithLikesCount(count(p.id) as bCount,c) from Post p join p.likes l group by p",
            countQuery = "select count(p) from Post p"
    )
    Page<PostWithLikesCount> findAllWithLikesCount(Pageable pageable);

    @Query(
            value = "select new package.PostWithLikesCount(count(p.id) as bCount,c) from Post p join p.likes l group by p order by bCount asc",
            countQuery = "select count(p) from Post p"
    )
    Page<PostWithLikesCount> findAllWithLikesCountOrderByCountAsc(Pageable pageable);

    @Query(
            value = "select new package.PostWithLikesCount(count(p.id),p) from Post p join p.likes l group by p order by bCount desc",
            countQuery = "select count(p) from Post p"
    )
    Page<PostWithLikesCount> findAllWithLikesCountOrderByCountDesc(Pageable pageable);
}
