package com.pedrycz.cinehub.model.mappers;

import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-05T14:52:25+0200",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 19.0.2 (Amazon.com Inc.)"
)
@Component
public class MovieToMovieDTOMapperImpl implements MovieToMovieDTOMapper {

    @Override
    public MovieDTO movieToMovieDTO(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        String id = null;
        String title = null;
        String plot = null;
        Float rating = null;
        String releaseYear = null;
        Integer runtime = null;
        String posterUrl = null;
        List<String> genres = null;
        List<String> directors = null;
        List<String> cast = null;

        id = movie.getId();
        title = movie.getTitle();
        plot = movie.getPlot();
        rating = movie.getRating();
        releaseYear = movie.getReleaseYear();
        runtime = movie.getRuntime();
        posterUrl = movie.getPosterUrl();
        List<String> list = movie.getGenres();
        if ( list != null ) {
            genres = new ArrayList<String>( list );
        }
        List<String> list1 = movie.getDirectors();
        if ( list1 != null ) {
            directors = new ArrayList<String>( list1 );
        }
        List<String> list2 = movie.getCast();
        if ( list2 != null ) {
            cast = new ArrayList<String>( list2 );
        }

        MovieDTO movieDTO = new MovieDTO( id, title, plot, rating, releaseYear, runtime, posterUrl, genres, directors, cast );

        return movieDTO;
    }

    @Override
    public Movie movieDTOToMovie(MovieDTO movieDTO) {
        if ( movieDTO == null ) {
            return null;
        }

        String title = null;
        String plot = null;
        String releaseYear = null;
        Integer runtime = null;
        String posterUrl = null;
        List<String> genres = null;
        List<String> directors = null;
        List<String> cast = null;

        title = movieDTO.title();
        plot = movieDTO.plot();
        releaseYear = movieDTO.releaseYear();
        runtime = movieDTO.runtime();
        posterUrl = movieDTO.posterUrl();
        List<String> list = movieDTO.genres();
        if ( list != null ) {
            genres = new ArrayList<String>( list );
        }
        List<String> list1 = movieDTO.directors();
        if ( list1 != null ) {
            directors = new ArrayList<String>( list1 );
        }
        List<String> list2 = movieDTO.cast();
        if ( list2 != null ) {
            cast = new ArrayList<String>( list2 );
        }

        Movie movie = new Movie( title, plot, releaseYear, runtime, posterUrl, genres, directors, cast );

        movie.setId( movieDTO.id() );
        movie.setRating( movieDTO.rating() );

        return movie;
    }
}
