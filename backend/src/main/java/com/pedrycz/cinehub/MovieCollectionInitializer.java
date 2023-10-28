package com.pedrycz.cinehub;

import com.pedrycz.cinehub.model.entities.Role;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieCollectionInitializer implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final MovieRepository movieRepository;
    private final RoleRepository roleRepository;
    private final Job initDatabaseJob;

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {
            log.info("Initializing database...");
            JobParameters jobParameters = new JobParameters(
                    Map.of("time", new JobParameter<>(System.currentTimeMillis(), Long.class))
            );
            jobLauncher.run(initDatabaseJob, jobParameters);
        }

        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(new Role(1, "ROLE_ADMIN"), new Role(0, "ROLE_USER")));

            log.info("Database initialized");
        }
    }
}
