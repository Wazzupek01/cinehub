package com.pedrycz.cinehub.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PosterService {

    String addPoster(String filename, MultipartFile file);
    Object getPoster(HttpServletRequest request);
    void deletePoster(String filename);
}
