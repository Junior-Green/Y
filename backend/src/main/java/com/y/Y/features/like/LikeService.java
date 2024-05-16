package com.y.Y.features.like;

import java.util.List;
import java.util.UUID;

public interface LikeService {

    List<Like> getLikesByUserId(UUID userId);

    List<Like> getLikesByPostId(UUID postId);
}
