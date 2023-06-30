package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.services.interfaces.PosterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poster")
@Tag(name = "Poster", description = "Get single poster for requested movie from Minio Storage only")
public class PosterController {

    private final PosterService posterService;

    @Autowired
    public PosterController(PosterService posterService) {
        this.posterService = posterService;
    }

    @GetMapping(value = "/{filename}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Poster for this movie id not found in Minio"),
            @ApiResponse(responseCode = "200", description = "Poster found for requested movie",
                    content = @Content(mediaType = "image/png"))
    })
    @Operation(summary = "Get poster stored in Minio", description = "Returns file described in path variable if exists in Minio storage")
    public ResponseEntity<Object> getFile(@PathVariable String filename) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(posterService.getByFilename(filename));
    }
}
