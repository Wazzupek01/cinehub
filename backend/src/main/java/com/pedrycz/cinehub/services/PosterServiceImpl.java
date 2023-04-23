package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.exceptions.BadFileException;
import com.pedrycz.cinehub.exceptions.PosterNotFoundException;
import com.pedrycz.cinehub.helpers.Constants;
import com.pedrycz.cinehub.services.interfaces.PosterService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println(filename);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
        } catch (Exception e) {
            throw new BadFileException(filename);
        }

        return getPreSignedUrl(filename);
    }

    public Object getPoster(HttpServletRequest request) {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket("cinehub")
                    .object(filename)
                    .build());
            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new PosterNotFoundException(filename);
        }
    }

    @Override
    public void deletePoster(String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(filename).build());
        } catch (Exception e){
            throw new PosterNotFoundException(filename);
        }
    }

    private String getPreSignedUrl(String filename) {
        return Constants.SERVER_ADDRESS + "/poster/" + filename;
    }
}
