package com.pedrycz.cinehub.model.dto.review;

import com.pedrycz.cinehub.model.dto.movie.SimpleMovieDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewWithMovieDTO(
        UUID id,
        Integer rating,
        LocalDateTime timestamp,
        String content,
        SimpleMovieDTO movie
){}
