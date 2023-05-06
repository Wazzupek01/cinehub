package com.pedrycz.cinehub.services.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PosterService {

    String addPoster(String filename, MultipartFile file);
    Object getPoster(String id);
    void deletePoster(String filename);
}
