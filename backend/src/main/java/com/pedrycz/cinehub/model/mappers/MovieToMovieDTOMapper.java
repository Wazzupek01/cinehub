package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieToMovieDTOMapper {
    MovieDTO movieToMovieDTO(Movie movie);
    Movie movieDTOToMovie(MovieDTO movieDTO);
}
