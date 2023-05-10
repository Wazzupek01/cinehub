package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.model.dto.movie.MovieDTO;
import com.pedrycz.cinehub.services.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/review")
@Tag(name = "Review", description = "Endpoints for reviews management")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review successfully added",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "403", description = "Review not added due to lack of authorization", content = @Content),
            @ApiResponse(responseCode = "404", description = "Movie doesn't exist", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid argument (rating) or user already reviewed this movie", content = @Content)
    })
    @Operation(summary = "Add review", description = "Add new review by active user for selected movie")
    public ResponseEntity<ReviewDTO> addReview(@CookieValue("jwt") String token, @Valid @RequestBody ReviewDTO reviewDTO){
        return new ResponseEntity<>(reviewService.addReview(token, reviewDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reviewId}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review with requested id deleted", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Review not deleted due to lack of authorization or is not owned by this user", content = @Content),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @Operation(summary = "Remove review", description = "Remove review if created by active user")
    public ResponseEntity<HttpStatus> removeReview(@CookieValue("jwt") String token, @PathVariable String reviewId){
        reviewService.removeReview(token, reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{reviewId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review with requested id found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewDTO.class))),
            @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @Operation(summary = "Get review by ID", description = "Find review using its' id")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable String reviewId){
        return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
    }

    @GetMapping("/movie/recent/{movieId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found most recent reviews for a movie with content",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ReviewDTO.class),
                            arraySchema = @Schema(implementation = Set.class)
                    ))),
            @ApiResponse(responseCode = "404", description = "Reviews or movie not found", content = @Content)
    })
    @Operation(summary = "Get most recent reviews for movie",
            description = "Get 5 most recent reviews with content for a movie")
    public ResponseEntity<Set<ReviewDTO>> getMostRecentReviewsWithContentForMovie(@PathVariable String movieId) {
        return new ResponseEntity<>(reviewService.getMostRecentReviewsWithContentForMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}/{pageNum}/{orderBy}/{isAscending}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "movie page with requested parameters found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "Requested page not found", content = @Content)
    })
    @Operation(summary = "Get reviews for a movie", description = "Get reviews for a movie with content, sorted by TIMESTAMP, or RATING")
    public ResponseEntity<Page<ReviewDTO>> getReviewsWithContentByMovieId(@PathVariable String movieId,
                                                                         @PathVariable int pageNum,
                                                                         @PathVariable String orderBy,
                                                                         @PathVariable boolean isAscending){
        Page<ReviewDTO> reviewPage = reviewService.getReviewsWithContentByMovieId(movieId,
                new GetParams(pageNum, orderBy, isAscending));
        return new ResponseEntity<>(reviewPage, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/{pageNum}/{orderBy}/{isAscending}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "movie page with requested parameters found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "404", description = "Requested page not found", content = @Content),
    })
    @Operation(summary = "Get reviews of a user", description = "Get all reviews created by user, sorted by TIMESTAMP, or RATING")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByUserId(@PathVariable String userId,@PathVariable int pageNum,
                                                              @PathVariable String orderBy,
                                                              @PathVariable boolean isAscending){
        Page<ReviewDTO> reviewPage = reviewService.getReviewsByUserId(userId, new GetParams(pageNum, orderBy, isAscending));
        return new ResponseEntity<>(reviewPage, HttpStatus.OK);
    }

    @GetMapping("/movie/all/{movieId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "reviews for a movie found",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = ReviewDTO.class),
                            arraySchema = @Schema(implementation = Set.class)
                    ))),
            @ApiResponse(responseCode = "404", description = "Movie doesn't exist or has no reviews", content = @Content)
    })
    @Operation(summary = "Get all reviews for movie", description = "Get a set of all reviews for specified movie")
    public ResponseEntity<Set<ReviewDTO>> getAllReviewsForMovie(@PathVariable String movieId){
        return new ResponseEntity<>(reviewService.getReviewsByMovieId(movieId), HttpStatus.OK);
    }

    @GetMapping("/movie/all/{movieId}/{pageNum}/{orderBy}/{isAscending}")
    @Operation(summary = "Get page from all reviews for movie", description = "Get page of all reviews (with and without content) for requested movie, sorted by TIMESTAMP, or RATING")
    public ResponseEntity<Page<ReviewDTO>> getPageFromAllReviewsForMovie(@PathVariable String movieId,@PathVariable int pageNum,
                                                                         @PathVariable String orderBy,
                                                                         @PathVariable boolean isAscending){
        Page<ReviewDTO> reviewPage = reviewService.getReviewsByMovieId(movieId, new GetParams(pageNum, orderBy, isAscending));
        return new ResponseEntity<>(reviewPage, HttpStatus.OK);
    }
}
