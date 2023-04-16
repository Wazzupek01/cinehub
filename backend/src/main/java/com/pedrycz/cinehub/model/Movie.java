package com.pedrycz.cinehub.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Getter
@Setter
@Document
public class Movie {
    @Id
    private String id;
    private String title;
    private String plot;
    private String releaseYear;
    private Integer runtime;
    private String posterUrl;
    private List<String> genres;
    private List<String> directors;
    private List<String> cast;

    public Movie(String title, String plot, String releaseYear, Integer runtime, String posterUrl, List<String> genres, List<String> directors, List<String> cast) {
        this.title = title;
        this.plot = plot;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.directors = directors;
        this.cast = cast;
    }
}
