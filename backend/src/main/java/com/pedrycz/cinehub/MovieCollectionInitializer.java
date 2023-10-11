package com.pedrycz.cinehub;

import com.pedrycz.cinehub.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieCollectionInitializer implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final MovieRepository movieRepository;
    private final Job initDatabaseJob;

    public MovieCollectionInitializer(JobLauncher jobLauncher, MovieRepository movieRepository,
                                      @Qualifier("initDatabaseJob") Job initDatabaseJob) {
        this.jobLauncher = jobLauncher;
        this.movieRepository = movieRepository;
        this.initDatabaseJob = initDatabaseJob;
    }

    @Override
    public void run(String... args) throws Exception {
        if(movieRepository.count() == 0) {
            log.info("Initializing database...");
            JobParameters jobParameters = new JobParameters();
            jobLauncher.run(initDatabaseJob, jobParameters);
        }
        log.info("Database initialized");
    }
}
