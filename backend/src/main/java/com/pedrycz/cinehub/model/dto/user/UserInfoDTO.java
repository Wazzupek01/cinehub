package com.pedrycz.cinehub.model.dto.user;

import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.dto.review.ReviewWithMovieDTO;

import java.util.Set;

public record UserInfoDTO(

        String nickname,
        Set<MovieDTO> watchLater,
        Set<ReviewWithMovieDTO> myReviews
) {
}
