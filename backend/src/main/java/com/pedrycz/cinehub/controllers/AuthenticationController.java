package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.dto.user.UserLoginDTO;
import com.pedrycz.cinehub.model.dto.user.UserRegisterDTO;
import com.pedrycz.cinehub.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody UserRegisterDTO request, HttpServletResponse response){
        Cookie jwt = new Cookie("jwt", authenticationService.register(request).getToken());
        jwt.setMaxAge(24*60*60);
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        jwt.setPath("/");
        response.addCookie(jwt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<HttpStatus> register(@RequestBody UserLoginDTO request, HttpServletResponse response){
        Cookie jwt = new Cookie("jwt", authenticationService.authenticate(request).getToken());
        jwt.setMaxAge(24*60*60);
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        jwt.setPath("/");
        response.addCookie(jwt);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
