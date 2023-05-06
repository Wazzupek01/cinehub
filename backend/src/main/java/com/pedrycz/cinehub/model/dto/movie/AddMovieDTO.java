package com.pedrycz.cinehub.model.dto.movie;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddMovieDTO(@NonNull @NotBlank String title,
                          @NonNull @NotBlank String plot,
                          @NonNull @NotBlank String releaseYear,
                          @DecimalMin(value = "0", inclusive = false) Integer runtime,
                          @NonNull MultipartFile posterFile,
                          @NonNull List<String> genres,
                          @NonNull List<String> directors,
                          @NonNull List<String> cast) {
}
