package com.pedrycz.cinehub.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.naming.Name;

@Getter
@Setter
@AllArgsConstructor
public class BadWordApiResponseDto {

    String original;
    String censored;
    
    @JsonProperty("has_profanity")
    boolean hasProfanity;
}
