package com.pedrycz.cinehub.services.interfaces;

import com.pedrycz.cinehub.controllers.SortParams;
import com.pedrycz.cinehub.model.MovieQueryParams;
import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MovieService {

    
    
    MovieDTO getById(UUID id);
    
    Page<MovieDTO> getBy(MovieQueryParams params,  SortParams sortParams);

//    Page<MovieDTO> getAll(SortParams params);
//
//    Page<MovieDTO> getShorts(SortParams params);
//
//    Page<MovieDTO> getFullLength(SortParams params);

    MovieDTO add(AddMovieDTO movie);

    MovieDTO updateById(UUID id, AddMovieDTO movieDTO);

    void deleteById(UUID id);
}
