package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.model.entities.Review;
import com.pedrycz.cinehub.repositories.MovieRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReviewToReviewDTOMapper {

    @Named("reviewToReviewDTO")
    static ReviewDTO reviewToReviewDTO(Review review){
        return new ReviewDTO(review.getRating(), review.getContent(), review.getMovie().getId());
    }

    static Set<ReviewDTO> reviewSetToReviewDTOSet(Set<Review> reviews){
        Set<ReviewDTO> reviewDTOs = new HashSet<>();
        for(Review r: reviews){
            reviewDTOs.add(reviewToReviewDTO(r));
        }
        return reviewDTOs;
    }

    Review reviewDTOToReview(ReviewDTO reviewDTO);
}
