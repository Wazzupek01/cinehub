package com.pedrycz.cinehub.batch;

import com.pedrycz.cinehub.exceptions.IncompleteDocumentException;
import com.pedrycz.cinehub.model.entities.Movie;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
@RequiredArgsConstructor
public class DatabaseInitJobConfiguration {
    
    private final BsonMovieObjectReader movieObjectReader;

    @Bean
    Job initDatabaseJob(JobRepository jobRepository,
                        @Qualifier("readMoviesFromBsonAndWriteToPostgresStep") Step readMoviesAndWriteToMongoStep) {
        return new JobBuilder("Init database", jobRepository)
                .start(readMoviesAndWriteToMongoStep)
                .build();
    }

    @Bean
    Step readMoviesFromBsonAndWriteToPostgresStep(JobRepository jobRepository,
                                                  PlatformTransactionManager transactionManager,
                                                  ItemWriter<Movie> writer) {
        return new StepBuilder("Read movies from BSON and write to Postgres", jobRepository)
                .<Movie, Movie>chunk(100, transactionManager)
                .reader(reader())
                .writer(writer)
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
    
    @Bean
    ItemWriter<Movie> writer(EntityManager entityManager) {
        JpaItemWriter<Movie> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return writer;
    }
}
