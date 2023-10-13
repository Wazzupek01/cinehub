package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;

import java.util.UUID;

public interface UserService {

    UserInfoDTO getUserInfoByNickname(String nickname);

    UserInfoDTO getUserInfoById(UUID id);

    void addMovieToWatchLater(String userToken, UUID movieId);
    void removeMovieFromWatchLater(String userToken, UUID movieId);
}
