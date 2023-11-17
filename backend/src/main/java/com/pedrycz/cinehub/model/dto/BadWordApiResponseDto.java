package com.pedrycz.cinehub.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BadWordApiResponseDto(
        String original,
        String censored,
        @JsonProperty("has_profanity")
        boolean hasProfanity
) {
}