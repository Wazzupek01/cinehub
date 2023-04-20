package com.pedrycz.cinehub.model.entities;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Getter
@Setter
@Document
public class Movie {

    @Id
    private String id;

    @NonNull
    @NotBlank
    private String title;
    private Float rating;

    @NonNull
    @NotBlank
    private String plot;

    @NonNull
    @NotBlank
    private String releaseYear;

    @DecimalMin(value = "0", inclusive = false)
    private Integer runtime;
    private String posterUrl;

    @NonNull
    @NotBlank
    private List<String> genres;

    @NonNull
    @NotBlank
    private List<String> directors;

    @NonNull
    @NotBlank
    private List<String> cast;

    @DocumentReference
    private List<Review> reviews;

    public Movie(@NotNull String title, @NotNull String plot, @NotNull String releaseYear, Integer runtime, String posterUrl,
                 @NotNull List<String> genres, @NotNull List<String> directors, @NotNull List<String> cast) {
        this.title = title;
        this.plot = plot;
        this.rating = 0F;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.directors = directors;
        this.cast = cast;
    }
}
