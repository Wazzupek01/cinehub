package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.movie.AddMovieDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.services.interfaces.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/id")
    public ResponseEntity<MovieDTO> getMovieById(@RequestBody String id){
        return new ResponseEntity<>(movieService.getById(id), HttpStatus.FOUND);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByTitle(@PathVariable String title, @RequestBody GetParams params){
        System.out.println(title);
        return new ResponseEntity<>(movieService.getByTitle(title, params), HttpStatus.FOUND);
    }

    @GetMapping("/director/{director}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByDirector(@PathVariable String director, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByDirector(director, params), HttpStatus.FOUND);
    }

    @GetMapping("/actor/{actor}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByActor(@PathVariable String actor, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByActor(actor, params), HttpStatus.FOUND);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByGenre(@PathVariable String genre, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByGenre(genre, params), HttpStatus.FOUND);
    }

    @GetMapping("/runtime/shorts")
    public ResponseEntity<Page<MovieDTO>> getShorts(@RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getShorts(params), HttpStatus.FOUND);
    }

    @GetMapping("/runtime/full")
    public ResponseEntity<Page<MovieDTO>> getFullLength(@RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getFullLength(params), HttpStatus.FOUND);
    }

    @GetMapping("/runtime/{min}/{max}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByRuntime(@PathVariable int min, @PathVariable int max, @RequestBody GetParams params){
        return new ResponseEntity<>(movieService.getByRuntimeBetween(min, max, params), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<MovieDTO> addMovie(@Valid @ModelAttribute AddMovieDTO movie) {
        MovieDTO movieDTO = movieService.add(movie);
        return new ResponseEntity<>(movieDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMovie(@RequestBody String id){
        movieService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<MovieDTO> updateMovie(@Valid @RequestBody MovieDTO movieDTO){
        return new ResponseEntity<>(movieService.update(movieDTO), HttpStatus.OK);
    }
}
