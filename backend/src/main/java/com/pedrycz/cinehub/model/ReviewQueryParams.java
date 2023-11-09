package com.pedrycz.cinehub.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReviewQueryParams {
    UUID reviewId;
    UUID movieId;
    UUID userId;
    Boolean withContent;
}
