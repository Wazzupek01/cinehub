package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import com.pedrycz.cinehub.model.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReviewToReviewDTOMapper {

    @Named("reviewToReviewDTO")
    static ReviewDTO reviewToReviewDTO(Review review) {
        return new ReviewDTO(review.getId(), review.getRating(), review.getTimestamp(),
                review.getContent(), review.getMovie().getId(), review.getUser().getId());
    }

    @Named("reviewSetToReviewDTOSet")
    static Set<ReviewDTO> reviewSetToReviewDTOSet(Set<Review> reviews) {
        Set<ReviewDTO> reviewDTOs = new HashSet<>();
        for (Review r : reviews) {
            reviewDTOs.add(reviewToReviewDTO(r));
        }
        return reviewDTOs;
    }
}
