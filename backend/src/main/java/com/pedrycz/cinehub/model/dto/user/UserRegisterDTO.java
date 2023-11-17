package com.pedrycz.cinehub.model.dto.user;

import com.pedrycz.cinehub.validation.Nickname;
import com.pedrycz.cinehub.validation.Password;
import com.pedrycz.cinehub.validation.UniqueEmail;
import jakarta.validation.constraints.Email;

public record UserRegisterDTO(
        @Nickname String nickname,
        @Password String password,
        @Email
        @UniqueEmail
        String email
) {
}
