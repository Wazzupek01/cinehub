package com.pedrycz.cinehub.batch.bad_words_cleaning_job;

import com.pedrycz.cinehub.model.entities.Review;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BadWordsCleaningJobConfiguration {
    
    @Bean
    Job badWordsCleaningJob(JobRepository jobRepository,
                            @Qualifier("removeBadWordsStep") Step removeBadWordsStep) {
        return new JobBuilder("Bad words cleaning job", jobRepository)
                .start(removeBadWordsStep)
                .build();
    }

    @Bean
    Step removeBadWordsStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager,
                            ItemReader<Review> reader,
                            ReviewItemProcessor processor,
                            ItemWriter<Review> writer) {
        return new StepBuilder("Remove bad words step", jobRepository)
                .<Review, Review>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
    @Bean
    ItemReader<Review> reviewReader(EntityManager entityManager) {
        return new JpaCursorItemReaderBuilder<Review>()
                .name("Review reader")
                .entityManagerFactory(entityManager.getEntityManagerFactory())
                .queryString("SELECT r FROM Review r WHERE r.content != '' and r.content != null")
                .build();
    }

    @Bean
    ItemWriter<Review> reviewItemWriter(EntityManager entityManager) {
        JpaItemWriter<Review> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManager.getEntityManagerFactory());
        return writer;
    }
}
