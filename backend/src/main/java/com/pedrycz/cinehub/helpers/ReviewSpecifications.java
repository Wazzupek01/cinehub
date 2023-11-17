package com.pedrycz.cinehub.helpers;

import com.pedrycz.cinehub.model.entities.Review;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ReviewSpecifications {
    
    public static Specification<Review> hasId(UUID id) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Review> isForMovie(UUID movieId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<Review> hasContent() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("content"));
    }

    public static Specification<Review> createdByUserWithId(UUID userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }
}
