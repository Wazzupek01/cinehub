package com.pedrycz.cinehub.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface PosterService {

    String add(String filename, MultipartFile file);
    Object getByFilename(String filename);
    void deleteByFilename(String filename);
}
