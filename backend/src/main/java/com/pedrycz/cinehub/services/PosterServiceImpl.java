package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.exceptions.BadFileException;
import com.pedrycz.cinehub.exceptions.PosterNotFoundException;
import com.pedrycz.cinehub.helpers.Constants;
import com.pedrycz.cinehub.services.interfaces.PosterService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@Service
public class PosterServiceImpl implements PosterService {

    @Value("${minio.bucket.name}")
    private String bucketName;
    private final MinioClient minioClient;

    @Autowired
    public PosterServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String add(String filename, MultipartFile file) {
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

    public Object getByFilename(String filename) {
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
    public void deleteByFilename(String filename) {
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
