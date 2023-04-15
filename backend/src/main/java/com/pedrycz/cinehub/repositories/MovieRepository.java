package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
