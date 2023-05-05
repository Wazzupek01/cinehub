package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.services.interfaces.PosterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poster")
public class PosterController {

    private final PosterService posterService;

    @Autowired
    public PosterController(PosterService posterService) {
        this.posterService = posterService;
    }

    @GetMapping(value = "/**")
    public ResponseEntity<Object> getFile(HttpServletRequest request) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(posterService.getPoster(request));
    }
}
