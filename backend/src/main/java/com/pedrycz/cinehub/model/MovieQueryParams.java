package com.pedrycz.cinehub.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MovieQueryParams {
    private final String title;
    private final Float rating;
    private final String releaseYear;
    private final Integer minRuntime;
    private final Integer maxRuntime;
    private final String genre;
    private final String director;
    private final String actor;
}
