package com.y.Y.utils;

import jakarta.persistence.Entity;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PostSummaryViewDto {

    UUID getPOST_ID();

    Integer getLIKES_COUNT();

    LocalDateTime getFIRST_LIKE_DATE();

    LocalDateTime getLAST_LIKE_DATE();

}
