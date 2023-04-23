package com.pedrycz.cinehub.model.dto;

import java.util.List;

public record MovieDTO(String id,
                       String title,
                       String plot,
                       Float rating,
                       String releaseYear,
                       Integer runtime,
                       String posterUrl,
                       List<String> genres,
                       List<String> directors,
                       List<String> cast) {
}
