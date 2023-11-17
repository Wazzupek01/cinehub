package com.pedrycz.cinehub.model.dto.movie;

public record SimpleMovieDTO(
        String title,
        Float rating,
        String releaseYear,
        Integer runtime,
        String posterUrl
) {
}
