package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.MovieDTO;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public ResponseEntity<Page<MovieDTO>> getAllMovies(@RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getAll(params), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByTitle(@PathVariable String title, @RequestBody GetParams params){
        System.out.println(title);
        return new ResponseEntity<>(movieService.getByTitle(title, params), HttpStatus.OK);
    }

    @GetMapping("/director/{director}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByDirector(@PathVariable String director, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByDirector(director, params), HttpStatus.OK);
    }

    @GetMapping("/actor/{actor}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByActor(@PathVariable String actor, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByActor(actor, params), HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByGenre(@PathVariable String genre, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByGenre(genre, params), HttpStatus.OK);
    }

    @GetMapping("/runtime/shorts")
    public ResponseEntity<Page<MovieDTO>> getShorts(@RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getShorts( params), HttpStatus.OK);
    }

    @GetMapping("/runtime/full")
    public ResponseEntity<Page<MovieDTO>> getFullLength(@RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getFullLength(params), HttpStatus.OK);
    }

    @GetMapping("/runtime/{min}/{max}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByRuntime(@PathVariable int min, @PathVariable int max, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByRuntimeBetween(min, max, params), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<MovieDTO> addMovie(@ModelAttribute AddMovieDTO movie){
        MovieDTO movieDTO = movieService.add(movie);
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }
}
