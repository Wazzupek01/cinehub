package com.pedrycz.cinehub.model.dto.review;

import com.pedrycz.cinehub.model.dto.movie.SimpleMovieDTO;

import java.time.LocalDateTime;

public record ReviewWithMovieDTO(
        String id,
        Integer rating,
        LocalDateTime timestamp,
        String content,
        SimpleMovieDTO movie
){
    public ReviewWithMovieDTO(String id, Integer rating, LocalDateTime timestamp, String content, SimpleMovieDTO movie) {
        this.id = id;
        this.rating = rating;
        this.timestamp = timestamp;
        this.content = content;
        this.movie = movie;
    }
}
