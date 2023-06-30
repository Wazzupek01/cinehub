package com.pedrycz.cinehub.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfiguration {

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Bean
    @Primary
    public MinioClient minioClient() {

        MinioClient client = new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl)
                .build();
        try {
            if (!client.bucketExists(BucketExistsArgs.builder().bucket("cinehub").build())){
                client.makeBucket(MakeBucketArgs.builder().bucket("cinehub").build());
            }
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
        return client;
    }
}
