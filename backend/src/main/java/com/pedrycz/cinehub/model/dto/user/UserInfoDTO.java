package com.pedrycz.cinehub.model.dto.user;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import lombok.Data;

import java.util.Set;

@Data
public class UserInfoDTO {

    private String nickname;
    private Set<MovieDTO> watchLater;
    private Set<ReviewDTO> myReviews;

    public UserInfoDTO(String nickname, Set<MovieDTO> watchLater, Set<ReviewDTO> myReviews) {
        this.nickname = nickname;
        this.watchLater = watchLater;
        this.myReviews = myReviews;
    }
}
