package com.pedrycz.cinehub.batch.bad_words_cleaning_job;

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
import org.springframework.scheduling.support.CronExpression;

import java.util.Map;

public class BadWordsCleaner {
    
    private final JobLauncher jobLauncher;

    private final Job badWordsCleaningJob;

    public BadWordsCleaner(JobLauncher jobLauncher, @Qualifier("badWordsCleaningJob") Job badWordsCleaningJob) {
        this.jobLauncher = jobLauncher;
        this.badWordsCleaningJob = badWordsCleaningJob;
    }
    
    @Scheduled(cron = "0 */1 * * * *")
    public void run() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(badWordsCleaningJob, 
                new JobParameters( Map.of("time",new JobParameter<>(System.currentTimeMillis(), Long.class))));
    }
}
