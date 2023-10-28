package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.SortParams;
import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.UUID;

public interface ReviewService {

    ReviewDTO add(String userToken, ReviewDTO reviewDTO);
    void remove(String userToken, UUID reviewId);
    ReviewDTO getById(UUID id);
    Page<ReviewDTO> getByUserId(UUID userId, SortParams sortParams);
    Page<ReviewDTO> getByMovieId(UUID movieId, SortParams sortParams);
    Set<ReviewDTO> getSetByMovieId(UUID movieId);
    Page<ReviewDTO> getContainingContentByMovieId(UUID movieId, SortParams sortParams);
    Set<ReviewDTO> getSetOfMostRecentWithContentByMovieId(UUID movieId);
}
