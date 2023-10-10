package com.pedrycz.cinehub.model;

import com.pedrycz.cinehub.model.enums.GetMovieByParamName;

public record GetByParam<T, U>(
        T name,
        U value
) {
}
