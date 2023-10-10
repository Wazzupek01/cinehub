package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {

    @NonNull
    Page<Movie> findAll(@NonNull Pageable pageable);

    Optional<Movie> findMovieById(String id);

    Page<Movie> findMoviesByTitleIsContainingIgnoreCase(String title, Pageable pageable);

    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCase(String director, Pageable pageable);

    Page<Movie> findMoviesByCastIsContainingIgnoreCase(String actor, Pageable pageable);

    Page<Movie> findMoviesByGenresContainingIgnoreCase(String genre, Pageable pageable);

    Page<Movie> findMoviesByRuntimeBetween(Integer min, Integer max, Pageable pageable);

    Page<Movie> findMoviesByRuntimeGreaterThanEqual(Integer min, Pageable pageable);

    Page<Movie> findMoviesByRuntimeLessThan(Integer max, Pageable pageable);

    void deleteMovieById(String id);
}
