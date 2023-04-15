package com.pedrycz.cinehub.controllers;

import com.pedrycz.cinehub.model.FileDto;
import com.pedrycz.cinehub.services.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final MinioService minioService;

    @Autowired
    public FileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok(minioService.getListObjects());
    }
    @PostMapping(value = "/upload")
    public ResponseEntity<Object> upload(@ModelAttribute FileDto request) {
        return ResponseEntity.ok().body(minioService.uploadFile(request));
    }

}
