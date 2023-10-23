package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.exceptions.DocumentNotFoundException;
import com.pedrycz.cinehub.exceptions.IllegalParamTypeException;
import com.pedrycz.cinehub.exceptions.PageNotFoundException;
import com.pedrycz.cinehub.helpers.Constants;
import com.pedrycz.cinehub.helpers.SortUtils;
import com.pedrycz.cinehub.model.GetByParam;
import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.model.enums.GetMovieByParamName;
import com.pedrycz.cinehub.model.mappers.MovieToMovieDTOMapper;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import com.pedrycz.cinehub.services.interfaces.PosterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.pedrycz.cinehub.model.enums.GetMovieByParamName.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final PosterService posterService;
    private final MovieToMovieDTOMapper movieDTOMapper;

    @Override
    public MovieDTO getById(UUID id) {
        return movieDTOMapper.movieToMovieDTO(unwrapMovie(movieRepository.findMovieById(id), id.toString()));
    }

    @Override
    public Page<MovieDTO> getByTitleMatching(String title, GetParams getParams) {
        return getBy(new GetByParam<>(GetMovieByParamName.TITLE, title), getParams);
    }


    @Override
    public Page<MovieDTO> getAll(GetParams getParams) {
        return getBy(new GetByParam<>(GetMovieByParamName.ALL, null), getParams);
    }

    @Override
    public Page<MovieDTO> getByDirector(String director, GetParams getParams) {
        return getBy(new GetByParam<>(DIRECTOR, director), getParams);
    }

    @Override
    public Page<MovieDTO> getByActor(String actor, GetParams getParams) {
        return getBy(new GetByParam<>(GetMovieByParamName.ACTOR, actor), getParams);
    }

    @Override
    public Page<MovieDTO> getByGenre(String genre, GetParams getParams) {
        return getBy(new GetByParam<>(GENRE, genre), getParams);
    }

    @Override
    public Page<MovieDTO> getByRuntimeBetween(int min, int max, GetParams getParams) {
        return getBy(new GetByParam<>(RUNTIME_BETWEEN, Pair.of(min, max)), getParams);
    }

    @Override
    public Page<MovieDTO> getShorts(GetParams getParams) {
        return getBy(new GetByParam<>(GetMovieByParamName.SHORTS, null), getParams);
    }

    @Override
    public Page<MovieDTO> getFullLength(GetParams getParams) {
        return getBy(new GetByParam<>(GetMovieByParamName.FULL_LENGTH, null), getParams);
    }

    private <U> Page<MovieDTO> getBy(GetByParam<GetMovieByParamName, U> param, GetParams getParams) {
        PageRequest pageRequest = PageRequest.of(getParams.getPageNum(), 20)
                .withSort(SortUtils.getSort(getParams));

        Page<Movie> moviePage = switch (param.name()) {
            case DIRECTOR ->
                    movieRepository.findMoviesByDirectorsIsContainingIgnoreCase((String) param.value(), pageRequest);
            case GENRE -> movieRepository.findMoviesByGenresContainingIgnoreCase((String) param.value(), pageRequest);
            case RUNTIME_BETWEEN -> {
                if (!(param.value() instanceof Pair)) {
                    throw new IllegalParamTypeException(param.value().getClass(), Pair.class);
                }

                yield movieRepository.findMoviesByRuntimeBetween(
                        ((Pair<Integer, Integer>) param.value()).getFirst(),
                        ((Pair<Integer, Integer>) param.value()).getSecond(),
                        pageRequest
                );
            }
            case SHORTS -> movieRepository.findMoviesByRuntimeLessThan(60, pageRequest);
            case FULL_LENGTH -> movieRepository.findMoviesByRuntimeGreaterThanEqual(60, pageRequest);
            case ACTOR -> movieRepository.findMoviesByActorsIsContainingIgnoreCase((String) param.value(), pageRequest);
            case TITLE -> movieRepository.findMoviesByTitleIsContainingIgnoreCase((String) param.value(), pageRequest);
            default -> movieRepository.findAll(pageRequest);
        };


        try {
            if (!moviePage.getContent().isEmpty()) return moviePage.map(movieDTOMapper::movieToMovieDTO);
            throw new PageNotFoundException(getParams.getPageNum());
        } catch (NullPointerException e) {
            throw new PageNotFoundException(getParams.getPageNum());
        }
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
    public void deleteById(UUID id) {
        String posterUrl = unwrapMovie(movieRepository.findMovieById(id), id.toString()).getPosterUrl();
        if (posterUrl.contains(Constants.SERVER_ADDRESS)) posterService.deleteByFilename(id + Constants.FILE_TYPE);
        log.info("Deleting movie with id: {}", id);
        movieRepository.deleteMovieById(id);
    }

    public static Movie unwrapMovie(Optional<Movie> document, String paramValue) {
        return document.orElseThrow(() -> new DocumentNotFoundException(paramValue));
    }
}