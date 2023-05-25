package com.pedrycz.cinehub.model.dto.review;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.NonNull;

import java.time.LocalDateTime;

public record ReviewDTO (String id, @NonNull
@DecimalMin(value = "1")
@DecimalMax(value = "10")Integer rating, LocalDateTime timestamp , String content, String movieId, String userId){}
