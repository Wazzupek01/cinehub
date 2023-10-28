package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.model.entities.Movie;
import org.springframework.data.jpa.domain.Specification;

import static java.lang.StringTemplate.STR;

public class MovieSpecifications {

    public static Specification<Movie> hasTitleLike(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), title.toLowerCase());
    }

    public static Specification<Movie> hasDirectorLike(String director) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("directors")), STR. "%\{ director.toLowerCase() }%");
    }

    public static Specification<Movie> hasActorLike(String actor) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("actors")), STR. "%\{ actor.toLowerCase() }%");
    }

    public static Specification<Movie> hasGenre(String genre) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("genres")), STR. "%\{ genre.toLowerCase() }%");
    }

    public static Specification<Movie> hasRuntimeBetween(int min, int max) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("runtime"), min, max);
    }
}
