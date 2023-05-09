package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findReviewById(String id);
    Page<Review> findReviewsByUserId(String userId, Pageable pageable);
    Page<Review> findReviewsByUserIdOrderByRatingDesc(String userId, Pageable pageable);
    Page<Review> findReviewsByUserIdOrderByRatingAsc(String userId, Pageable pageable);
    Page<Review> findReviewsByUserIdOrderByTimestampDesc(String userId, Pageable pageable);
    Page<Review> findReviewsByUserIdOrderByTimestampAsc(String userId, Pageable pageable);

    Page<Review> findReviewsByMovieId(String movieId, Pageable pageable);

    Set<Review> findReviewsByMovieId(String movieId);
    @Query("{ 'movie' : ObjectId(?0), '$expr': { '$gt': [{ '$strLenCP': '$content' }, 0 ] }}")
    Page<Review> findReviewsByMovieIdWithReviewNotEmpty(String MovieId, Pageable pageable); // Use Sort parameter in PageRequest here
}
