package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.services.interfaces.PosterService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

@Service
public class PosterServiceImpl implements PosterService {

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final MinioClient minioClient;

    @Autowired
    public PosterServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String addPoster(String filename, MultipartFile file) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
        } catch (Exception e) {
            System.out.println("Error");
        }

        return getPreSignedUrl(filename);
    }

    public Object getPoster(HttpServletRequest request) throws IOException {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("cinehub")
                    .object(filename)
                    .build());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
        return IOUtils.toByteArray(stream);
    }

    private String getPreSignedUrl(String filename) {
        return "http://localhost:8080/poster/".concat(filename);
    }
}
