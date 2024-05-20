package com.y.Y.features.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, String> {

    @Query("SELECT h from HashTag h WHERE h.hashtag = ?1")
    public HashTag findByHashtagName(String hashtag);
}
