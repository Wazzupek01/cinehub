package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.MovieDTO;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{pageNum}")
    public ResponseEntity<List<MovieDTO>> getAllMovies(@PathVariable int pageNum, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getAll(pageNum, params).getContent(), HttpStatus.OK);
    }
}
