package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.review.ReviewWithMovieDTO;
import com.pedrycz.cinehub.model.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper
public abstract class ReviewWithMovieDTOMapper {
    
    public static ReviewWithMovieDTO ReviewToReviewWithMovieDTOMapper(Review review) {
        MovieToSimpleMovieDTOMapper movieMapper = Mappers.getMapper(MovieToSimpleMovieDTOMapper.class);
        return new ReviewWithMovieDTO(review.getId(), review.getRating(), review.getTimestamp(), review.getContent(),
                movieMapper.movieToSimpleMovieDTO(review.getMovie()));
    }

    public Set<ReviewWithMovieDTO> ReviewSetToReviewWithMovieDTOSetMapper(Set<Review> reviews) {
        Set<ReviewWithMovieDTO> reviewDTOs = new HashSet<>();
        for (Review r : reviews) {
            reviewDTOs.add(ReviewToReviewWithMovieDTOMapper(r));
        }
        return reviewDTOs;
    }
}
