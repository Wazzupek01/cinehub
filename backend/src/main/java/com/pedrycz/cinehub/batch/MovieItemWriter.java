package com.pedrycz.cinehub.batch;

import com.pedrycz.cinehub.model.entities.Movie;
import com.pedrycz.cinehub.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MovieItemWriter implements ItemWriter<Movie> {

    private final MovieRepository repository;

    @Override
    public void write(Chunk<? extends Movie> chunk) throws Exception {
        repository.saveAll(chunk);
    }
}
