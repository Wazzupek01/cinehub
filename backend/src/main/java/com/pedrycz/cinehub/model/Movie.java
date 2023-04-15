package com.pedrycz.cinehub.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Getter
@Setter
@Document
public class Movie {
    @Id
    private String id;
    private String title;
    private Integer budget;
    private Date releaseDate;
    private Integer length;
    private String distributor;
    private String genre;
    private String director;

    public Movie(String title, Integer budget, Date releaseDate, Integer length, String distributor, String genre, String director) {
        this.title = title;
        this.budget = budget;
        this.releaseDate = releaseDate;
        this.length = length;
        this.distributor = distributor;
        this.genre = genre;
        this.director = director;
    }
}
