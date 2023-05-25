package com.pedrycz.cinehub.model.dto.user;

import com.pedrycz.cinehub.model.dto.review.ReviewDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.dto.review.ReviewWithMovieDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UserInfoDTO {

    private String nickname;
    private Set<MovieDTO> watchLater;
    private Set<ReviewWithMovieDTO> myReviews;

    public UserInfoDTO(String nickname, Set<MovieDTO> watchLater, Set<ReviewWithMovieDTO> myReviews) {
        this.nickname = nickname;
        this.watchLater = watchLater;
        this.myReviews = myReviews;
    }
}
