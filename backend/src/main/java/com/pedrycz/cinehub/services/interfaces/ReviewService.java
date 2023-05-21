package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.model.dto.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ReviewService {

    ReviewDTO addReview(String token, ReviewDTO reviewDTO);
    void removeReview(String token, String reviewId);
    ReviewDTO getReviewById(String id);
    Page<ReviewDTO> getReviewsByUserId(String userId, GetParams getParams);
    Page<ReviewDTO> getReviewsByMovieId(String movieId, GetParams getParams);
    Set<ReviewDTO> getReviewsByMovieId(String movieId);
    Page<ReviewDTO> getReviewsWithContentByMovieId(String movieId, GetParams getParams);
    Set<ReviewDTO> getMostRecentReviewsWithContentForMovie(String movieId);
}
