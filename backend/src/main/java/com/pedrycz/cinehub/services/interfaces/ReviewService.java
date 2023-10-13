package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.UUID;

public interface ReviewService {

    ReviewDTO add(String userToken, ReviewDTO reviewDTO);
    void remove(String userToken, UUID reviewId);
    ReviewDTO getById(UUID id);
    Page<ReviewDTO> getByUserId(UUID userId, GetParams getParams);
    Page<ReviewDTO> getByMovieId(UUID movieId, GetParams getParams);
    Set<ReviewDTO> getSetByMovieId(UUID movieId);
    Page<ReviewDTO> getContainingContentByMovieId(UUID movieId, GetParams getParams);
    Set<ReviewDTO> getSetOfMostRecentWithContentByMovieId(UUID movieId);
}
