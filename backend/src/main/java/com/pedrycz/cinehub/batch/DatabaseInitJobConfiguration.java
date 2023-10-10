package com.pedrycz.cinehub.batch;

import com.pedrycz.cinehub.exceptions.IncompleteDocumentException;
import com.pedrycz.cinehub.model.entities.Movie;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitJobConfiguration {

    private final MovieItemWriter movieItemWriter;
    private final BsonMovieObjectReader movieObjectReader;

    @Bean
    Job initDatabaseJob(JobRepository jobRepository,
                        @Qualifier("readMoviesFromBsonAndWriteToMongoStep") Step readMoviesAndWriteToMongoStep) {
        return new JobBuilder("Init database", jobRepository)
                .start(readMoviesAndWriteToMongoStep)
                .build();
    }

    @Bean
    Step readMoviesFromBsonAndWriteToMongoStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("Read movies from BSON and write to Mongo", jobRepository)
                .<Movie, Movie>chunk(100, transactionManager)
                .reader(reader())
                .writer(movieItemWriter)
                .faultTolerant()
                .skip(IncompleteDocumentException.class)
                .skipLimit(10000)
                .build();
    }

    @Bean
    @SneakyThrows
    JsonItemReader<Movie> reader() {
        JsonItemReader<Movie> reader = new JsonItemReader<>();
        reader.setResource(new ClassPathResource("movies.json"));
        reader.setJsonObjectReader(movieObjectReader);
        reader.setName("movieItemReader");
        return reader;
    }
}
