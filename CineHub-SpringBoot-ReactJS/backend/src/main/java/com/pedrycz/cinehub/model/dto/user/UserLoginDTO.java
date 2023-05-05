package com.pedrycz.cinehub.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserLoginDTO {

    @NonNull
    @NotBlank
    private String email;

    @NonNull
    @NotBlank
    private String password;
}
