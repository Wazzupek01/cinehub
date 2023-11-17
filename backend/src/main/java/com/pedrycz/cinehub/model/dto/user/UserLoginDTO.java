package com.pedrycz.cinehub.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UserLoginDTO (
    @NonNull @NotBlank String email,
    @NonNull @NotBlank String password
) {}
