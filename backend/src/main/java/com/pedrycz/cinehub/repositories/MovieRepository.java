package com.pedrycz.cinehub.repositories;

import com.pedrycz.cinehub.model.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>, 
        JpaSpecificationExecutor<Movie> {
    
    Optional<Movie> findMovieById(UUID id);
    
//    @Query(nativeQuery = true, value = """
//            select distinct * from movie m
//            join directors d on m.id = d.movie_id
//            where d.directors like :director
//            """)
//    Page<Movie> findMovieByDirector(String director, Pageable pageable);
//    Page<Movie> findMovieByActor(String director, Pageable pageable);
//    Page<Movie> findMovieByGenre(String director, Pageable pageable);
    void deleteById(UUID id);
}
