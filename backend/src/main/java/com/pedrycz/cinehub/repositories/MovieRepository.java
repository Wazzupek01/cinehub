package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {

    Optional<Movie> findMovieById(String id);
    List<Movie> findMoviesByTitle(String title);
    @NotNull
    Page<Movie> findAll(@NotNull Pageable pageable);

    Page<Movie> findAllByOrderByRatingDesc(Pageable pageable);
    Page<Movie> findAllByOrderByRatingAsc(Pageable pageable);
    Page<Movie> findAllByOrderByReleaseYearDesc(Pageable pageable);
    Page<Movie> findAllByOrderByReleaseYearAsc(Pageable pageable);
    Page<Movie> findAllByOrderByRuntimeDesc(Pageable pageable);
    Page<Movie> findAllByOrderByRuntimeAsc(Pageable pageable);

    List<Movie> findMoviesByDirectorsIsContainingIgnoreCase(String director);
    List<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingDesc(String director);
    List<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingAsc(String director);
    List<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearDesc(String director);
    List<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearAsc(String director);
    List<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeDesc(String director);
    List<Movie> findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeAsc(String director);

    List<Movie> findMoviesByCastIsContainingIgnoreCase(String actor);
    List<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRatingDesc(String actor);
    List<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRatingAsc(String actor);
    List<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearDesc(String actor);
    List<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearAsc(String actor);
    List<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeDesc(String actor);
    List<Movie> findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeAsc(String actor);



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
