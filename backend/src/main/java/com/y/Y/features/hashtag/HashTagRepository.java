package com.y.Y.features.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashTagRepository extends JpaRepository<HashTag, String> {

    @Query("SELECT h from HashTag h WHERE h.name = ?1")
    public HashTag findByHashtagName(String hashtag);
}
