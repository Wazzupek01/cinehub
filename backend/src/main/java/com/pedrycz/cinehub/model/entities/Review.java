package com.pedrycz.cinehub.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Getter
@Setter
@Document
public class Review {

    @Id
    private String id;
    private Integer rating;
    private String review;

    @DocumentReference(lazy = true)
    private Movie movie;

    public Review(Integer rating, String review, Movie movie) {
        this.rating = rating;
        this.review = review;
        this.movie = movie;
    }
}
