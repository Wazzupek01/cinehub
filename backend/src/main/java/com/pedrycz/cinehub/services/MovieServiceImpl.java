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
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.mappers.MovieToMovieDTOMapper;
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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final PosterService posterService;
    private final MovieToMovieDTOMapper movieDTOMapper;


    @Override
    public Page<MovieDTO> getBy(MovieQueryParams params, SortParams sortParams) {
        PageRequest pageRequest = PageRequest.of(sortParams.pageNum(), 20)
                .withSort(SortUtils.getSort(sortParams));

        Specification<Movie> specification = getQuerySpecification(params);
        Page<Movie> moviePage = movieRepository.findAll(specification, pageRequest);

        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
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
        return movieDTOMapper.movieToMovieDTO(unwrapMovie(movieRepository.findMovieById(id), id.toString()));
    }

    @Override
    public MovieDTO add(AddMovieDTO movie) {
        Movie inserted = movieRepository.save(new Movie(movie.title(),
                movie.plot(),
                movie.releaseYear(),
                movie.runtime(),
                "",
                movie.genres(), movie.directors(), movie.cast()));

        inserted.setPosterUrl(posterService.add(inserted.getId() + Constants.FILE_TYPE, movie.posterFile()));

        log.info("Adding movie: {}", inserted.getTitle());
        return movieDTOMapper.movieToMovieDTO(movieRepository.save(inserted));
    }

    @Override
    @Transactional
    public MovieDTO updateById(UUID id, AddMovieDTO movieDTO) {
        Optional<Movie> movie = movieRepository.findMovieById(id);
        if (movie.isPresent()) {
            Movie updatedMovie = movie.get();
            updatedMovie.setActors(movieDTO.cast());
            updatedMovie.setPlot(movieDTO.plot());
            updatedMovie.setId(id);
            updatedMovie.setGenres(movieDTO.genres());
            updatedMovie.setDirectors(movieDTO.directors());
            updatedMovie.setReleaseYear(movieDTO.releaseYear());
            updatedMovie.setRuntime(movieDTO.runtime());
            updatedMovie.setTitle(movieDTO.title());
            updatedMovie.setPosterUrl(posterService.add(updatedMovie.getId() + Constants.FILE_TYPE, movieDTO.posterFile()));
            log.info("Updating data for movie: {}", updatedMovie.getTitle());
            return movieDTOMapper.movieToMovieDTO(movieRepository.save(updatedMovie));
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