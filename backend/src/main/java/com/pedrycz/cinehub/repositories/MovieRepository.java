package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>, 
        JpaSpecificationExecutor<Movie> {
    
    Optional<Movie> findMovieById(UUID id);
    void deleteById(UUID id);
}
