package com.pedrycz.cinehub.model.dto.user;

import com.pedrycz.cinehub.validation.Nickname;
import com.pedrycz.cinehub.validation.Password;
import com.pedrycz.cinehub.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @Nickname
    private String nickname;

    @Password
    private String password;

    @Email
    @UniqueEmail
    private String email;
}
