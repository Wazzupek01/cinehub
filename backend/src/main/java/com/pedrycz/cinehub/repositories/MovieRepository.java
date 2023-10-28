package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>, 
        JpaSpecificationExecutor<Movie> {

//    @NonNull
//    Page<Movie> findAll(@NonNull Pageable pageable);

    Optional<Movie> findMovieById(UUID id);

//    Page<Movie> findMoviesByTitleIsContainingIgnoreCase(String title, Pageable pageable);
//    
//    @Query("SELECT m from Movie m join m.directors d where lower(d) like lower(concat('%', :director, '%'))")
//    Page<Movie> findMoviesByDirectorsIsContainingIgnoreCase(@Param("director") String director, Pageable pageable);
//
//    @Query("SELECT m from Movie m join m.actors a where lower(a) like lower(concat('%', :actor, '%'))")
//    Page<Movie> findMoviesByActorsIsContainingIgnoreCase(@Param("actor") String actor, Pageable pageable);
//
//    @Query("SELECT m from Movie m join m.genres g where lower(g) like lower(concat('%', :genre, '%'))")
//    Page<Movie> findMoviesByGenresContainingIgnoreCase(@Param("genre") String genre, Pageable pageable);
//
//    Page<Movie> findMoviesByRuntimeBetween(Integer min, Integer max, Pageable pageable);
//
//    Page<Movie> findMoviesByRuntimeGreaterThanEqual(Integer min, Pageable pageable);
//
//    Page<Movie> findMoviesByRuntimeLessThan(Integer max, Pageable pageable);

    void deleteMovieById(UUID id);
}
