package com.pedrycz.cinehub.services;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BadWordsCleaningService {
    
    private final JobLauncher jobLauncher;

    private final Job badWordsCleaningJob;

    public BadWordsCleaningService(JobLauncher jobLauncher, @Qualifier("badWordsCleaningJob") Job badWordsCleaningJob) {
        this.jobLauncher = jobLauncher;
        this.badWordsCleaningJob = badWordsCleaningJob;
    }
    
    @Scheduled(cron = "0 0 */1 * * *")
    public void run() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(badWordsCleaningJob, 
                new JobParameters( Map.of("time",new JobParameter<>(System.currentTimeMillis(), Long.class))));
    }
}
