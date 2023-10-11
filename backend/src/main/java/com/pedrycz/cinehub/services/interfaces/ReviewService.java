package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ReviewService {

    ReviewDTO add(String userToken, ReviewDTO reviewDTO);
    void remove(String userToken, String reviewId);
    ReviewDTO getById(String id);
    Page<ReviewDTO> getByUserId(String userId, GetParams getParams);
    Page<ReviewDTO> getByMovieId(String movieId, GetParams getParams);
    Set<ReviewDTO> getSetByMovieId(String movieId);
    Page<ReviewDTO> getContainingContentByMovieId(String movieId, GetParams getParams);
    Set<ReviewDTO> getSetOfMostRecentWithContentByMovieId(String movieId);
}
