package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieToMovieDTOMapper {
    MovieDTO movieToMovieDTO(Movie movie);
    List<MovieDTO> movieListToMovieDTOList(List<Movie> movies);
}