package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.movie.SimpleMovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import org.mapstruct.Mapper;

@Mapper
public interface MovieToSimpleMovieDTOMapper {
    SimpleMovieDTO movieToSimpleMovieDTO(Movie movie);
}
