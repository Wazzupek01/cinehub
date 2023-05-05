package com.pedrycz.cinehub.model.entities;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Document
public class Review {

    @Id
    private String id;

    @NonNull
    @DecimalMin(value = "1", inclusive = true)
    @DecimalMax(value = "10", inclusive = true)
    private Integer rating;
    private String content;
    private LocalDateTime timestamp;

    @DocumentReference(lazy = true)
    private Movie movie;

    @DocumentReference(lazy = true)
    private User user;

    public Review(Integer rating, String content, Movie movie, User user) {
        this.rating = rating;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.movie = movie;
        this.user = user;
    }
}
