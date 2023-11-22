package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, String>, JpaSpecificationExecutor<Review> {
    Optional<Review> findReviewById(UUID id);

    Set<Review> findReviewsByMovieId(UUID movieId);

    @Query("SELECT r FROM Review r WHERE r.movie = ?1 and r.content != '' and r.content != null")
    Page<Review> findReviewsByMovieWithContentNotEmpty(Movie Movie, Pageable pageable);
}
