package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.user.UserInfoDTO;
import com.pedrycz.cinehub.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User", description = "Requests for retrieving specific users information")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/nickname/{nickname}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "204", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class)))
    })
    @Operation(summary = "Get user info", description = "Get simple user informations")
    public ResponseEntity<UserInfoDTO> getUserByNickname(@PathVariable String nickname){
        return new ResponseEntity<>(userService.getUserInfoByNickname(nickname), HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "204", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class)))
    })
    @Operation(summary = "Get user info", description = "Get simple user informations by id")
    public ResponseEntity<UserInfoDTO> getUserInfoById(@PathVariable UUID id){
        return new ResponseEntity<>(userService.getUserInfoById(id), HttpStatus.OK);
    }

    @PostMapping("/watchLater/add/{movieId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content),
            @ApiResponse(responseCode = "200", description = "Movie added to watch later", content = @Content),
            @ApiResponse(responseCode = "400", description = "Jwt cookie not provided", content = @Content)
    })
    @Operation(summary = "Add to Watch Later", description = "Add specified movie to watch later set of logged in user")
    public ResponseEntity<HttpStatus> addMovieToWatchLater(@CookieValue("jwt") String token, @PathVariable UUID movieId){
        userService.addMovieToWatchLater(token, movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/watchLater/remove/{movieId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content),
            @ApiResponse(responseCode = "204", description = "Movie removed from watch later", content = @Content),
            @ApiResponse(responseCode = "400", description = "Jwt cookie not provided", content = @Content)
    })
    @Operation(summary = "Remove from Watch Later", description = "Remove specified movie from watch later set of logged in user")
    public ResponseEntity<HttpStatus> removeMovieFromWatchLater(@CookieValue("jwt") String token, @PathVariable UUID movieId){
        userService.removeMovieFromWatchLater(token, movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
