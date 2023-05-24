package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MovieToMovieDTOMapper {
    MovieDTO movieToMovieDTO(Movie movie);

    Movie movieDTOToMovie(MovieDTO movieDTO);
}
