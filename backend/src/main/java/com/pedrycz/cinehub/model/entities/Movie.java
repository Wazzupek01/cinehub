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
@AllArgsConstructor
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_genre",
            joinColumns = { @JoinColumn(name = "movie_id")},
            inverseJoinColumns = { @JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres;

    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_director",
            joinColumns = { @JoinColumn(name = "movie_id")},
            inverseJoinColumns = { @JoinColumn(name = "director_id")}
    )
    private Set<Director> directors;

    @NonNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_actor",
            joinColumns = { @JoinColumn(name = "movie_id")},
            inverseJoinColumns = { @JoinColumn(name = "actor_id")}
    )
    private Set<Actor> actors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @ManyToMany(mappedBy = "watchLater")
    private Set<User> watchedLater;


    public Movie(@NotNull String title, @NotNull String plot, @NotNull String releaseYear, Integer runtime, String posterUrl,
                 @NotNull Set<Genre> genres, @NotNull Set<Director> directors, @NotNull Set<Actor> actors) {
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
