package com.pedrycz.cinehub.batch.init_database_job;

import com.pedrycz.cinehub.exceptions.IncompleteDocumentException;
import com.pedrycz.cinehub.model.entities.Actor;
import com.pedrycz.cinehub.model.entities.Director;
import com.pedrycz.cinehub.model.entities.Genre;
import com.pedrycz.cinehub.model.entities.Movie;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Component
@Slf4j
public class BsonMovieObjectReader implements JsonObjectReader<Movie> {

    private BufferedReader bufferedReader;
    private final Set<Actor> actors = new HashSet<>();
    private final Set<Genre> genres = new HashSet<>();
    private final Set<Director> directors = new HashSet<>();
    
    @Override
    public Movie read() throws Exception {
        String line;
        if ((line = bufferedReader.readLine()) != null) {
            Document document = Document.parse(line);
            try {
                Set<Genre> movieGenres = prepareGenres((List<String>) getField(document, "genres"));
                Set<Director> movieDirectors = prepareDirectors((List<String>) getField(document, "directors"));
                Set<Actor> movieActors = prepareActors((List<String>) getField(document, "cast"));
                
                return new Movie(getField(document, "title").toString(),
                        getField(document, "plot").toString(),
                        getField(document, "year").toString(),
                        (Integer) getField(document, "runtime"),
                        getField(document, "poster").toString(),
                        movieGenres,
                        movieDirectors,
                        movieActors
                        );
            } catch (NullPointerException e) {
                throw new IncompleteDocumentException();
            }
        } else {
            return null;
        }
    }

    @Override
    public void open(@NotNull Resource resource) throws Exception {
        JsonObjectReader.super.open(resource);
        bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }

    @Override
    public void close() throws Exception {
        JsonObjectReader.super.close();
    }

    private Set<Actor> prepareActors(List<String> actorNames) {
        if(actorNames.isEmpty()) return Set.of();
        Set<Actor> movieActors = new HashSet<>();
        for(String actorName: actorNames) {
            Optional<Actor> existing = actors.stream().filter(actor -> actorName.equals(actor.getName())).findFirst();
            if(existing.isPresent()) {
                movieActors.add(existing.get());
            } else {
                Actor newActor = new Actor(actors.size() + 1L, actorName);
                this.actors.add(newActor);
                movieActors.add(newActor);
            }
        }
        
        return movieActors;
    }

    private Set<Genre> prepareGenres(List<String> genreNames) {
        if(genreNames.isEmpty()) return Set.of();
        Set<Genre> movieGenres = new HashSet<>();
        for(String genreName: genreNames) {
            Optional<Genre> existing = genres.stream().filter(genre -> genreName.equals(genre.getName())).findFirst();
            if(existing.isPresent()) {
                movieGenres.add(existing.get());
            } else {
                Genre newGenre = new Genre(actors.size() + 1L, genreName);
                this.genres.add(newGenre);
                movieGenres.add(newGenre);
            }
        }

        return movieGenres;
    }

    private Set<Director> prepareDirectors(List<String> directorNames) {
        if(directorNames.isEmpty()) return Set.of();
        Set<Director> movieDirectors = new HashSet<>();
        for(String directorName: directorNames) {
            Optional<Director> existing = directors.stream().filter(director -> directorName.equals(director.getName())).findFirst();
            if(existing.isPresent()) {
                movieDirectors.add(existing.get());
            } else {
                Director newDirector = new Director(actors.size() + 1L, directorName);
                this.directors.add(newDirector);
                movieDirectors.add(newDirector);
            }
        }

        return movieDirectors;
    }
    
    private Object getField(Document document, String fieldName) throws NullPointerException {
        Object field = document.get(fieldName);
        if (field == null) {
            throw new NullPointerException("Field not found");
        }
        return field;
    }
}
