package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.model.dto.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.mappers.MovieToMovieDTOMapper;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final MovieToMovieDTOMapper movieDTOMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.movieDTOMapper = Mappers.getMapper(MovieToMovieDTOMapper.class);
    }

    @Override
    public MovieDTO getById(String id) {
        return movieDTOMapper.movieToMovieDTO(unwrapMovie(movieRepository.findMovieById(id), id));
    }

    @Override
    public List<MovieDTO> getByTitle(String title) {
        return movieDTOMapper.movieListToMovieDTOList(movieRepository.findMoviesByTitle(title));
    }

    @Override
    public Page<MovieDTO> getAll(int pageNum, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(pageNum, 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findAll(pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findAllByOrderByRatingAsc(pageRequest);
                    else moviePage = movieRepository.findAllByOrderByRatingDesc(pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findAllByOrderByReleaseYearAsc(pageRequest);
                    else moviePage = movieRepository.findAllByOrderByReleaseYearDesc(pageRequest);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) moviePage = movieRepository.findAllByOrderByRuntimeAsc(pageRequest);
                    else moviePage = movieRepository.findAllByOrderByRuntimeDesc(pageRequest);
                }
            }
        }

        return moviePage != null ? moviePage.map(movieDTOMapper::movieToMovieDTO) : null;
    }

    @Override
    public List<MovieDTO> getByDirector(String director, GetParams getParams) {
        List<Movie> movies = new ArrayList<>();
        if(getParams.getOrderBy() == null){
            movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCase(director);
        } else {
            switch (getParams.getOrderBy()) {
                case "RATING" -> {
                    if (getParams.isAscending()) movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingAsc(director);
                    else movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingDesc(director);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearAsc(director);
                    else movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearDesc(director);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeAsc(director);
                    else movies = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeDesc(director);
                }
            }
        }

        return movieDTOMapper.movieListToMovieDTOList(movies);
    }

    @Override
    public List<MovieDTO> getByActor(String actor, GetParams getParams) {
        List<Movie> movies = new ArrayList<>();
        if(getParams.getOrderBy() == null){
            movies = movieRepository.findMoviesByCastIsContainingIgnoreCase(actor);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) movies = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRatingAsc(actor);
                    else movies = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRatingDesc(actor);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) movies = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearAsc(actor);
                    else movies = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearDesc(actor);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) movies = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeAsc(actor);
                    else movies = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeDesc(actor);
                }
            }
        }

        return movieDTOMapper.movieListToMovieDTOList(movies);
    }

    @Override
    public Page<MovieDTO> getByGenrePage(String genre, int pageNum, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(pageNum, 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByGenresContainingIgnoreCase(genre, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByGenresContainingIgnoreCaseOrderByRatingAsc(genre, pageRequest);
                    else moviePage = movieRepository.findMoviesByGenresContainingIgnoreCaseOrderByRatingDesc(genre, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByGenresContainingIgnoreCaseOrderByReleaseYearAsc(genre, pageRequest);
                    else moviePage = movieRepository.findMoviesByGenresContainingIgnoreCaseOrderByReleaseYearDesc(genre, pageRequest);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) moviePage = movieRepository.findMoviesByGenresContainingIgnoreCaseOrderByRuntimeAsc(genre, pageRequest);
                    else moviePage = movieRepository.findMoviesByGenresContainingIgnoreCaseOrderByRuntimeDesc(genre, pageRequest);
                }
            }
        }

        return moviePage != null ? moviePage.map(movieDTOMapper::movieToMovieDTO) : null;
    }

    @Override
    public Page<MovieDTO> getByRuntimeBetween(int min, int max, int pageNum, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(pageNum, 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByRuntimeBetween(min, max, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeBetweenOrderByRatingAsc(min, max, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeBetweenOrderByRatingDesc(min, max, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeBetweenOrderByReleaseYearAsc(min, max, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeBetweenOrderByReleaseYearDesc(min, max, pageRequest);
                }
                case "RUNTIME" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeBetweenOrderByRuntimeAsc(min, max, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeBetweenOrderByRuntimeDesc(min, max, pageRequest);
                }
            }
        }

        return moviePage != null ? moviePage.map(movieDTOMapper::movieToMovieDTO) : null;
    }

    @Override
    public Page<MovieDTO> getShortsPage(int pageNum, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(pageNum, 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByRuntimeLessThan(60, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeLessThanOrderByRatingAsc(60, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeLessThanOrderByRatingDesc(60, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeLessThanOrderByReleaseYearAsc(60, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeLessThanOrderByReleaseYearDesc(60, pageRequest);
                }
                case "RUNTIME" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeLessThanOrderByRuntimeAsc(60, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeLessThanOrderByRuntimeDesc(60, pageRequest);
                }
            }
        }

        return moviePage != null ? moviePage.map(movieDTOMapper::movieToMovieDTO) : null;
    }

    @Override
    public Page<MovieDTO> getFullLengthPage(int pageNum, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(pageNum, 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqual(60, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqualOrderByRatingAsc(60, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqualOrderByRatingDesc(60, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqualOrderByReleaseYearAsc(60, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqualOrderByReleaseYearDesc(60, pageRequest);
                }
                case "RUNTIME" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqualOrderByRuntimeAsc(60, pageRequest);
                    else moviePage = movieRepository.findMoviesByRuntimeGreaterThanEqualOrderByRuntimeDesc(60, pageRequest);
                }
            }
        }

        return moviePage != null ? moviePage.map(movieDTOMapper::movieToMovieDTO) : null;
    }

    @Override
    public MovieDTO add(Movie movie) {
        return movieDTOMapper.movieToMovieDTO(movieRepository.insert(movie));
    }

    @Override
    public MovieDTO update(String id, Movie movie) {
        return movieDTOMapper.movieToMovieDTO(movieRepository.save(movie));
    }

    @Override
    public void deleteById(String id) {
        movieRepository.deleteById(id);
    }

    public static Movie unwrapMovie(Optional<Movie> document, String paramValue) {
        if (document.isPresent()) return document.get();
        throw new DocumentNotFoundException(paramValue);
    }
}
