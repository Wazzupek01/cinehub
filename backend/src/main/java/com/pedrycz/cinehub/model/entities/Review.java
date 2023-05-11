package com.pedrycz.cinehub.model.entities;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Getter
@Setter
@Document
public class Review {

    @Id
    private String id;

    @NonNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "10")
    private Integer rating;
    private String content;
    private LocalDateTime timestamp;

    @DocumentReference(lazy = true)
    private Movie movie;

    @DocumentReference(lazy = true)
    private User user;

    public Review(@NonNull Integer rating, String content, Movie movie, User user) {
        this.rating = rating;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.movie = movie;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(rating, review.rating)
                && Objects.equals(content, review.content)
                && Objects.equals(timestamp, review.timestamp)
                && Objects.equals(movie, review.movie) && Objects.equals(user, review.user);
    }
}
