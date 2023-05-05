package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.model.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReviewToReviewDTOMapper {

    @Named("reviewToReviewDTO")
    static ReviewDTO reviewToReviewDTO(Review review){
        return new ReviewDTO(review.getId(), review.getRating(), review.getContent(), review.getMovie().getId());
    }
    Review reviewDTOToReview(ReviewDTO reviewDTO);
}
