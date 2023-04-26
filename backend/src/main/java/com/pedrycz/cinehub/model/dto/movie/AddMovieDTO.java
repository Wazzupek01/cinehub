package com.pedrycz.cinehub.model.dto.movie;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddMovieDTO(String title,
                       String plot,
                       String releaseYear,
                       Integer runtime,
                       MultipartFile posterFile,
                       List<String> genres,
                       List<String> directors,
                       List<String> cast) {
}
