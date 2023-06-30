package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;

public interface UserService {

    UserInfoDTO getUserInfo(String nickname);

    UserInfoDTO getUserInfoById(String id);

    void addMovieToWatchLater(String userToken, String movieId);
    void removeMovieFromWatchLater(String userToken, String movieId);
}
