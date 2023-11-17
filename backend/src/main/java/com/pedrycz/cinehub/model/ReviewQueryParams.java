package com.pedrycz.cinehub.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewQueryParams(
        UUID reviewId,
        UUID movieId,
        UUID userId,
        Boolean withContent
) {
}
