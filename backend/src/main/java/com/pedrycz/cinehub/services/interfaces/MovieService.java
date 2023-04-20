package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.GetParams;
import com.pedrycz.cinehub.model.dto.MovieDTO;
import com.pedrycz.cinehub.model.entities.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {

    MovieDTO getById(String id);
    List<MovieDTO> getByTitle(String title);
    Page<MovieDTO> getAll(int pageNum, GetParams params);
    List<MovieDTO> getByDirector(String director, GetParams params);
    List<MovieDTO> getByActor(String actor,  GetParams params);
    Page<MovieDTO> getByGenrePage(String genre, int pageNum, GetParams params);
    Page<MovieDTO> getByRuntimeBetween(int min, int max, int pageNum, GetParams params);
    Page<MovieDTO> getShortsPage(int pageNum, GetParams params);
    Page<MovieDTO> getFullLengthPage(int pageNum, GetParams params);
    MovieDTO add(Movie movie);
    MovieDTO update(String id, Movie movie);
    void deleteById(String id);
}
