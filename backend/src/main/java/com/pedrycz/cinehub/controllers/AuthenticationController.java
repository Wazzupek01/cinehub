package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.user.UserLoginDTO;
import com.pedrycz.cinehub.model.dto.user.UserRegisterDTO;
import com.pedrycz.cinehub.security.AuthenticationResponse;
import com.pedrycz.cinehub.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication operations, registering new accounts, logging in existing users." +
        " JWT Token is returned as HttpOnly cookie.")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Value("${security.key-expiration-time}")
    private int KEY_EXPIRATION_TIME;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials")
    })
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO request, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.register(request);
        Cookie jwt = new Cookie("jwt", authenticationResponse.getToken());
        jwt.setMaxAge(24*60*60);
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        jwt.setPath("/");
        response.addCookie(jwt);
        return new ResponseEntity<>(authenticationResponse.getNickname(), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Login", description = "Login user to existing account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user credentials")
    })
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO request, HttpServletResponse response){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        Cookie jwt = new Cookie("jwt", authenticationResponse.getToken());
        jwt.setMaxAge(24*60*60);
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        jwt.setPath("/");
        response.addCookie(jwt);
        return new ResponseEntity<>(authenticationResponse.getNickname(), HttpStatus.OK);
    }

    @GetMapping("/logout")
    @SecurityRequirement(name = "Bearer authentication")
    @Operation(summary = "Logout", description = "Logout active user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged out"),
    })
    public ResponseEntity<String> logout(HttpServletResponse response){
        Cookie jwt = new Cookie("jwt", "");
        jwt.setMaxAge(0);
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        jwt.setPath("/");
        response.addCookie(jwt);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
