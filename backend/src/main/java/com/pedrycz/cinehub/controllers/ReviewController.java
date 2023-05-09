package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.ReviewDTO;
import com.pedrycz.cinehub.services.interfaces.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                    description = "Review not added due to lack of authorization or is not owned by this user", content = @Content),
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

}
