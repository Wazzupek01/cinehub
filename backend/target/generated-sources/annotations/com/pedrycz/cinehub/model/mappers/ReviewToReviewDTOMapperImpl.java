package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import com.pedrycz.cinehub.model.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-05T15:57:23+0200",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class ReviewToReviewDTOMapperImpl implements ReviewToReviewDTOMapper {

    @Override
    public Review reviewDTOToReview(ReviewDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        Integer rating = null;
        String content = null;

        rating = reviewDTO.rating();
        content = reviewDTO.content();

        Movie movie = null;
        User user = null;

        Review review = new Review( rating, content, movie, user );

        review.setId( reviewDTO.id() );

        return review;
    }
}
