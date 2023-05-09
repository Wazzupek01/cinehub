package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.exceptions.PageNotFoundException;
import com.pedrycz.cinehub.helpers.Constants;
import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.mappers.MovieToMovieDTOMapper;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import com.pedrycz.cinehub.services.interfaces.PosterService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final PosterService posterService;
    private final MovieToMovieDTOMapper movieDTOMapper;


    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, PosterService posterService) {
        this.movieRepository = movieRepository;
        this.posterService = posterService;
        this.movieDTOMapper = Mappers.getMapper(MovieToMovieDTOMapper.class);
    }

    @Override
    public MovieDTO getById(String id) {
        return movieDTOMapper.movieToMovieDTO(unwrapMovie(movieRepository.findMovieById(id), id));
    }

    @Override
    public Page<MovieDTO> getByTitle(String title, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCase(title, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCaseOrderByRatingAsc(title, pageRequest);
                    else moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCaseOrderByRatingDesc(title, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCaseOrderByReleaseYearAsc(title, pageRequest);
                    else moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCaseOrderByReleaseYearDesc(title, pageRequest);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCaseOrderByRuntimeAsc(title, pageRequest);
                    else moviePage = movieRepository.findMoviesByTitleIsContainingIgnoreCaseOrderByRuntimeDesc(title, pageRequest);
                }
            }
        }

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getAll(GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
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

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getByDirector(String director, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCase(director, pageRequest);
        } else {
            switch (getParams.getOrderBy()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingAsc(director, pageRequest);
                    else moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRatingDesc(director, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearAsc(director, pageRequest);
                    else moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByReleaseYearDesc(director, pageRequest);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeAsc(director, pageRequest);
                    else moviePage = movieRepository.findMoviesByDirectorsIsContainingIgnoreCaseOrderByRuntimeDesc(director, pageRequest);
                }
            }
        }

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getByActor(String actor, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
        Page<Movie> moviePage = null;
        if(getParams.getOrderBy() == null){
            moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCase(actor, pageRequest);
        } else {
            switch (getParams.getOrderBy().toUpperCase()) {
                case "RATING" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRatingAsc(actor, pageRequest);
                    else moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRatingDesc(actor, pageRequest);
                }
                case "RELEASEYEAR" -> {
                    if (getParams.isAscending()) moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearAsc(actor, pageRequest);
                    else moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByReleaseYearDesc(actor, pageRequest);
                }
                case "RUNTIME" -> {
                    if(getParams.isAscending()) moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeAsc(actor, pageRequest);
                    else moviePage = movieRepository.findMoviesByCastIsContainingIgnoreCaseOrderByRuntimeDesc(actor, pageRequest);
                }
            }
        }

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getByGenre(String genre, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
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

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getByRuntimeBetween(int min, int max, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
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

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getShorts(GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
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

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public Page<MovieDTO> getFullLength(GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20);
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

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            else throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e){
            throw new PageNotFoundException(getParams.getPageNum());
        }
    }

    @Override
    public MovieDTO add(AddMovieDTO movie) {
        Movie inserted = movieRepository.insert(new Movie(movie.title(),
                movie.plot(),
                movie.releaseYear(),
                movie.runtime(),
                "",
                movie.genres(), movie.directors(), movie.cast()));

        inserted.setPosterUrl(posterService.addPoster(inserted.getId() + Constants.FILE_TYPE, movie.posterFile()));
        return movieDTOMapper.movieToMovieDTO(movieRepository.save(inserted));
    }

    @Override
    public MovieDTO update(String id, AddMovieDTO movieDTO) {
        Optional<Movie> movie = movieRepository.findMovieById(id);
        if(movie.isPresent()){
            Movie updatedMovie =  movie.get();
            updatedMovie.setCast(movieDTO.cast());
            updatedMovie.setPlot(movieDTO.plot());
            updatedMovie.setId(id);
            updatedMovie.setGenres(movieDTO.genres());
            updatedMovie.setDirectors(movieDTO.directors());
            updatedMovie.setReleaseYear(movieDTO.releaseYear());
            updatedMovie.setRuntime(movieDTO.runtime());
            updatedMovie.setTitle(movieDTO.title());
            updatedMovie.setPosterUrl(posterService.addPoster(updatedMovie.getId() + Constants.FILE_TYPE, movieDTO.posterFile()));
            return movieDTOMapper.movieToMovieDTO(movieRepository.save(updatedMovie));
        }
        throw new DocumentNotFoundException(id);
    }

    @Override
    public void deleteById(String id) {
        String posterUrl = unwrapMovie(movieRepository.findMovieById(id), id).getPosterUrl();
        if(posterUrl.contains(Constants.SERVER_ADDRESS)) posterService.deletePoster(id + Constants.FILE_TYPE);
        movieRepository.deleteById(id);
    }

    public static Movie unwrapMovie(Optional<Movie> document, String paramValue) {
        if (document.isPresent()) return document.get();
        throw new DocumentNotFoundException(paramValue);
    }
}
