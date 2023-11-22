package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.SortParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.exceptions.PageNotFoundException;
import com.pedrycz.cinehub.helpers.Constants;
import com.pedrycz.cinehub.helpers.MovieSpecifications;
import com.pedrycz.cinehub.helpers.SortUtils;
import com.pedrycz.cinehub.model.MovieQueryParams;
import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Actor;
import com.pedrycz.cinehub.model.entities.Director;
import com.pedrycz.cinehub.model.entities.Genre;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.mappers.MovieToMovieDTOMapper;
import com.pedrycz.cinehub.repositories.ActorRepository;
import com.pedrycz.cinehub.repositories.DirectorRepository;
import com.pedrycz.cinehub.repositories.GenreRepository;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import com.pedrycz.cinehub.services.interfaces.PosterService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final PosterService posterService;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;


    @Override
    public Page<MovieDTO> getBy(MovieQueryParams params, SortParams sortParams) {
        PageRequest pageRequest = PageRequest.of(sortParams.pageNum(), 20)
                .withSort(SortUtils.getSort(sortParams));
        Page<Movie> moviePage;

        Specification<Movie> specification = getQuerySpecification(params);
        moviePage = movieRepository.findAll(specification, pageRequest);

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(MovieToMovieDTOMapper::movieToMovieDTO);
            throw new PageNotFoundException(sortParams.pageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(sortParams.pageNum());
        }
    }

    private Specification<Movie> getQuerySpecification(MovieQueryParams params) {
        Specification<Movie> specification = Specification.where(null);

        if (params.title() != null) {
            specification = specification.and(MovieSpecifications.hasTitleLike(params.title()));
        }

        if (params.minRuntime() != null && params.maxRuntime() != null) {
            specification = specification.and(MovieSpecifications.hasRuntimeBetween(params.minRuntime(), params.maxRuntime()));
        }

        if (params.maxRuntime() != null) {
            specification = specification.and(MovieSpecifications.hasRuntimeUnder(params.maxRuntime()));
        }

        if (params.minRuntime() != null) {
            specification = specification.and(MovieSpecifications.hasRuntimeOver(params.minRuntime()));
        }

        if (params.genre() != null) {
            specification = specification.and(MovieSpecifications.hasGenre(params.genre()));
        }

        if (params.director() != null) {
            specification = specification.and(MovieSpecifications.hasDirectorLike(params.director()));
        }

        if (params.actor() != null) {
            specification = specification.and(MovieSpecifications.hasActorLike(params.actor()));
        }

        return specification;
    }

    @Override
    public MovieDTO getById(UUID id) {
        return MovieToMovieDTOMapper.movieToMovieDTO(unwrapMovie(movieRepository.findMovieById(id), id.toString()));
    }

    @Override
    public MovieDTO add(AddMovieDTO movie) {
        Set<Genre> genres = prepareGenres(movie.genres()) ;
        Set<Director> directors = prepareDirectors(movie.directors());
        Set<Actor> actors = prepareActors(movie.cast());

        Movie newMovie = movieRepository.save(new Movie(movie.title(),
                movie.plot(),
                movie.releaseYear(),
                movie.runtime(),
                "",
                genres,
                directors,
                actors
        ));
        
        newMovie.setPosterUrl(posterService.add(newMovie.getId() + Constants.FILE_TYPE, movie.posterFile()));

        log.info("Adding movie: {}", newMovie.getTitle());
        return MovieToMovieDTOMapper.movieToMovieDTO(movieRepository.save(newMovie));
    }

    private Set<Actor> prepareActors(List<String> actorNames) {
        Set<Actor> actors = actorRepository.findActorsByNameIn(actorNames);

        if (actors.size() == actorNames.size()) {
            return new HashSet<>(actors);
        }

        AtomicLong actorCount = new AtomicLong(genreRepository.count());
        List<String> foundActorNames = actors.stream().map(Actor::getName).toList();
        List<Actor> newActors = actorNames.stream().filter(name -> !foundActorNames.contains(name))
                .map(name -> new Actor(actorCount.getAndIncrement(), name)).toList();
        Set<Actor> movieActors = new HashSet<>(actors);
        movieActors.addAll(newActors);

        return movieActors;
    }

    private Set<Director> prepareDirectors(List<String> directorNames) {
        Set<Director> directors = directorRepository.findDirectorsByNameIn(directorNames);

        if (directors.size() == directorNames.size()) {
            return new HashSet<>(directors);
        }

        AtomicLong directorCount = new AtomicLong(genreRepository.count());
        List<String> foundDirectorNames = directors.stream().map(Director::getName).toList();
        List<Director> newDirectors = directorNames.stream().filter(name -> !foundDirectorNames.contains(name))
                .map(name -> new Director(directorCount.getAndIncrement(), name)).toList();
        Set<Director> movieDirectors = new HashSet<>(directors);
        movieDirectors.addAll(newDirectors);

        return movieDirectors;
    }

    private Set<Genre> prepareGenres(List<String> genreNames) {
        Set<Genre> genres = genreRepository.findGenresByNameIn(genreNames);

        if (genres.size() == genreNames.size()) {
            return new HashSet<>(genres);
        }

        AtomicLong genreCount = new AtomicLong(genreRepository.count());
        List<String> foundGenreNames = genres.stream().map(Genre::getName).toList();
        List<Genre> newGenres = genreNames.stream().filter(name -> !foundGenreNames.contains(name))
                .map(name -> new Genre(genreCount.getAndIncrement(), name)).toList();
        Set<Genre> movieGenres = new HashSet<>(genres);
        movieGenres.addAll(newGenres);

        return movieGenres;
    }

    @Override
    @Transactional
    public MovieDTO updateById(UUID id, AddMovieDTO movieDTO) {
        Optional<Movie> movie = movieRepository.findMovieById(id);
        if (movie.isPresent()) {
            Movie updatedMovie = movie.get();
            updatedMovie.setActors(prepareActors(movieDTO.cast()));
            updatedMovie.setPlot(movieDTO.plot());
            updatedMovie.setId(id);
            updatedMovie.setGenres(prepareGenres(movieDTO.genres()));
            updatedMovie.setDirectors(prepareDirectors(movieDTO.directors()));
            updatedMovie.setReleaseYear(movieDTO.releaseYear());
            updatedMovie.setRuntime(movieDTO.runtime());
            updatedMovie.setTitle(movieDTO.title());
            updatedMovie.setPosterUrl(posterService.add(updatedMovie.getId() + Constants.FILE_TYPE, movieDTO.posterFile()));
            log.info("Updating data for movie: {}", updatedMovie.getTitle());
            return MovieToMovieDTOMapper.movieToMovieDTO(movieRepository.save(updatedMovie));
        }

        throw new DocumentNotFoundException(id.toString());
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        String posterUrl = unwrapMovie(movieRepository.findMovieById(id), id.toString()).getPosterUrl();
        if (posterUrl.contains(Constants.SERVER_ADDRESS)) posterService.deleteByFilename(id + Constants.FILE_TYPE);
        log.info("Deleting movie with id: {}", id);
        movieRepository.deleteById(id);
    }

    public static Movie unwrapMovie(Optional<Movie> document, String paramValue) {
        return document.orElseThrow(() -> new DocumentNotFoundException(paramValue));
    }
}