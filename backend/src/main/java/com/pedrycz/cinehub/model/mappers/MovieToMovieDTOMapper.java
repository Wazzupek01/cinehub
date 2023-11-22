package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Actor;
import com.pedrycz.cinehub.model.entities.Director;
import com.pedrycz.cinehub.model.entities.Genre;
import com.pedrycz.cinehub.model.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieToMovieDTOMapper {
    
    static MovieDTO movieToMovieDTO(Movie movie){
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getPlot(),
                movie.getRating(),
                movie.getReleaseYear(),
                movie.getRuntime(), 
                movie.getPosterUrl(),
                movie.getGenres().stream().map(Genre::getName).toList(),
                movie.getDirectors().stream().map(Director::getName).toList(),
                movie.getActors().stream().map(Actor::getName).toList()
        );
    }
}
