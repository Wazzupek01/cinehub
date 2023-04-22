package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {

    @NotNull
    Page<Movie> findAll(@NotNull Pageable pageable);
    Optional<Movie> findMovieById(String id);

    Page<Movie> findAllByOrderByRatingDesc(Pageable pageable);
    Page<Movie> findAllByOrderByRatingAsc(Pageable pageable);
    Page<Movie> findAllByOrderByReleaseYearDesc(Pageable pageable);
    Page<Movie> findAllByOrderByReleaseYearAsc(Pageable pageable);
    Page<Movie> findAllByOrderByRuntimeDesc(Pageable pageable);
    Page<Movie> findAllByOrderByRuntimeAsc(Pageable pageable);

    Page<Movie> findMoviesByTitleIsContainingIgnoreCase(String title, Pageable pageable);
    Page<Movie> findMoviesByTitleIsContainingIgnoreCaseOrderByRatingDesc(String title, Pageable pageable);
    Page<Movie> findMoviesByTitleIsContainingIgnoreCaseOrderByRatingAsc(String title, Pageable pageable);
    Page<Movie> findMoviesByTitleIsContainingIgnoreCaseOrderByReleaseYearDesc(String title, Pageable pageable);
    Page<Movie> findMoviesByTitleIsContainingIgnoreCaseOrderByReleaseYearAsc(String title, Pageable pageable);
    Page<Movie> findMoviesByTitleIsContainingIgnoreCaseOrderByRuntimeDesc(String title, Pageable pageable);
    Page<Movie> findMoviesByTitleIsContainingIgnoreCaseOrderByRuntimeAsc(String title, Pageable pageable);

    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCase(String director,Pageable pageable);
    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingDesc(String director, Pageable pageable);
    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingAsc(String director, Pageable pageable);
    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearDesc(String director, Pageable pageable);
    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearAsc(String director, Pageable pageable);
    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeDesc(String director, Pageable pageable);
    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeAsc(String director, Pageable pageable);

    Page<Movie> findMoviesByCastIsContainingIgnoreCase(String actor, Pageable pageable);
    Page<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRatingDesc(String actor, Pageable pageable);
    Page<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRatingAsc(String actor, Pageable pageable);
    Page<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearDesc(String actor, Pageable pageable);
    Page<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearAsc(String actor, Pageable pageable);
    Page<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeDesc(String actor, Pageable pageable);
    Page<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeAsc(String actor, Pageable pageable);



    Page<Movie> findMoviesByGenresContainingIgnoreCase(String genre, Pageable pageable);
    Page<Movie> findMoviesByGenresContainingIgnoreCaseOrderByRatingDesc(String genre, Pageable pageable);
    Page<Movie> findMoviesByGenresContainingIgnoreCaseOrderByRatingAsc(String genre, Pageable pageable);
    Page<Movie> findMoviesByGenresContainingIgnoreCaseOrderByReleaseYearDesc(String genre, Pageable pageable);
    Page<Movie> findMoviesByGenresContainingIgnoreCaseOrderByReleaseYearAsc(String genre, Pageable pageable);
    Page<Movie> findMoviesByGenresContainingIgnoreCaseOrderByRuntimeDesc(String genre, Pageable pageable);
    Page<Movie> findMoviesByGenresContainingIgnoreCaseOrderByRuntimeAsc(String genre, Pageable pageable);

    Page<Movie> findMoviesByRuntimeBetween(Integer min, Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeBetweenOrderByRatingDesc(Integer min, Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeBetweenOrderByRatingAsc(Integer min, Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeBetweenOrderByReleaseYearDesc(Integer min, Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeBetweenOrderByReleaseYearAsc(Integer min, Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeBetweenOrderByRuntimeDesc(Integer min, Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeBetweenOrderByRuntimeAsc(Integer min, Integer max, Pageable pageable);

    Page<Movie>findMoviesByRuntimeGreaterThanEqual(Integer min, Pageable pageable);
    Page<Movie> findMoviesByRuntimeGreaterThanEqualOrderByRatingDesc(Integer min, Pageable pageable);
    Page<Movie> findMoviesByRuntimeGreaterThanEqualOrderByRatingAsc(Integer min, Pageable pageable);
    Page<Movie> findMoviesByRuntimeGreaterThanEqualOrderByReleaseYearDesc(Integer min, Pageable pageable);
    Page<Movie> findMoviesByRuntimeGreaterThanEqualOrderByReleaseYearAsc(Integer min, Pageable pageable);
    Page<Movie> findMoviesByRuntimeGreaterThanEqualOrderByRuntimeDesc(Integer min, Pageable pageable);
    Page<Movie> findMoviesByRuntimeGreaterThanEqualOrderByRuntimeAsc(Integer min, Pageable pageable);

    Page<Movie> findMoviesByRuntimeLessThan(Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeLessThanOrderByRatingDesc(Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeLessThanOrderByRatingAsc(Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeLessThanOrderByReleaseYearDesc(Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeLessThanOrderByReleaseYearAsc(Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeLessThanOrderByRuntimeDesc(Integer max, Pageable pageable);
    Page<Movie> findMoviesByRuntimeLessThanOrderByRuntimeAsc(Integer max, Pageable pageable);
}
