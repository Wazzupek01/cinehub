package com.pedrycz.cinehub.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NonNull
    @NotBlank
    private String title;
    private Float rating;

    @NonNull
    @NotBlank
    @Column(length = 1000)
    private String plot;

    @NonNull
    @NotBlank
    private String releaseYear;

    @DecimalMin(value = "0", inclusive = false)
    private Integer runtime;
    private String posterUrl;

    @NonNull
    @ElementCollection(targetClass = String.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "genres", joinColumns = @JoinColumn(name = "movie_id"))
    @JoinColumn(name = "movie_id")
    private List<String> genres;

    @NonNull
    @ElementCollection(targetClass = String.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "directors", joinColumns = @JoinColumn(name = "movie_id"))
    @JoinColumn(name = "movie_id")
    private List<String> directors;

    @NonNull
    @ElementCollection(targetClass = String.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "actors", joinColumns = @JoinColumn(name = "movie_id"))
    @JoinColumn(name = "movie_id")
    private List<String> actors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @ManyToMany(mappedBy = "watchLater")
    private Set<User> watchedLater;


    public Movie(@NotNull String title, @NotNull String plot, @NotNull String releaseYear, Integer runtime, String posterUrl,
                 @NotNull List<String> genres, @NotNull List<String> directors, @NotNull List<String> actors) {
        this.title = title;
        this.plot = plot;
        this.rating = 0F;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.posterUrl = posterUrl;
        this.genres = genres;
        this.directors = directors;
        this.actors = actors;
        this.reviews = new HashSet<>();
    }

    public void updateRating() {
        float rating = 0;

        if (reviews.isEmpty()) {
            this.rating = rating;
            return;
        }

        for (Review r : reviews) {
            rating += r.getRating();
        }
        
        rating /= reviews.size();

        this.rating = rating;
    }
}
