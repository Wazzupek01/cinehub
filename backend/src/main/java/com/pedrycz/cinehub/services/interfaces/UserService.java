package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;

public interface UserService {

    UserInfoDTO getUserInfo(String nickname);

    UserInfoDTO getUserInfoById(String id);

    void addToWatchLater(String token, String movieId);
    void removeFromWatchLater(String token, String movieId);
}
