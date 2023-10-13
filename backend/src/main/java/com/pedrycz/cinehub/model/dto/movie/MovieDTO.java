package com.pedrycz.cinehub.model.dto.movie;

import java.util.List;
import java.util.UUID;

public record MovieDTO(UUID id,
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
