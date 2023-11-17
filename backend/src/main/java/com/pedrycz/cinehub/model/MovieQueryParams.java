package com.pedrycz.cinehub.model;

import lombok.Builder;

@Builder
public record MovieQueryParams(
        String title,
        Float rating,
        String releaseYear,
        Integer minRuntime,
        Integer maxRuntime,
        String genre,
        String director,
        String actor


) {
    public static MovieQueryParams empty() {
        return builder().build();
    }
}
