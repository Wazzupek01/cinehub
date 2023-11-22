package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.model.entities.Actor;
import com.pedrycz.cinehub.model.entities.Director;
import com.pedrycz.cinehub.model.entities.Movie;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import static java.lang.StringTemplate.STR;

public class MovieSpecifications {

    public static Specification<Movie> hasTitleLike(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), STR. "%\{ title.toLowerCase() }%" );
    }

    public static Specification<Movie> hasDirectorLike(String director) {
        return (root, query, criteriaBuilder) -> {
            Join<Movie, Director> movieDirectorJoin = root.join("directors");
            return criteriaBuilder.like(criteriaBuilder.lower(movieDirectorJoin.get("name")), STR. "%\{ director.toLowerCase() }%" );
        };
    }

    public static Specification<Movie> hasActorLike(String actor) {
        return (root, query, criteriaBuilder) -> {
            Join<Movie, Actor> movieActorJoin = root.join("actors");
            return criteriaBuilder.like(criteriaBuilder.lower(movieActorJoin.get("name")), STR. "%\{ actor.toLowerCase() }%" );
        };
    }

    public static Specification<Movie> hasGenre(String genre) {
        return (root, query, criteriaBuilder) -> {
            Join<Movie, Director> movieGenreJoin = root.join("genres");
            return criteriaBuilder.like(criteriaBuilder.lower(movieGenreJoin.get("name")), STR. "%\{ genre.toLowerCase() }%" );
        };
    }

    public static Specification<Movie> hasRuntimeBetween(int min, int max) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("runtime"), min, max);
    }

    public static Specification<Movie> hasRuntimeUnder(int max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("runtime"), max);
    }

    public static Specification<Movie> hasRuntimeOver(int min) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("runtime"), min);
    }
}
