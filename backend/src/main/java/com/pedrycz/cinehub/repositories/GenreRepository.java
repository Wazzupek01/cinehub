package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    Set<Genre> findGenresByNameIn(List<String> genres);
}
