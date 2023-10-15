package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findReviewById(UUID id);
    Page<Review> findReviewsByUserId(UUID userId, Pageable pageable);
    Page<Review> findReviewsByMovieId(UUID movieId, Pageable pageable);

    Set<Review> findReviewsByMovieId(UUID movieId);
//    @Query("{ 'movie' : ObjectId(?0), '$expr': { '$gt': [{ '$strLenCP': '$content' }, 0 ] }}")
    @Query("SELECT r FROM Review r WHERE r.movie = ?1 and r.content != '' and r.content != null")
    Page<Review> findReviewsByMovieWithReviewNotEmpty(Movie MovieId, Pageable pageable); // Use Sort parameter in PageRequest here
}
