package com.example.springbatchdownversion.common.jobs;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class GlobalJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        MDC.put("jobName", jobExecution.getJobInstance().getJobName());
        MDC.put("executionId", jobExecution.getId().toString());

        log.info("[Job Start] JobName: {}, ID: {}",
                jobExecution.getJobInstance().getJobName(), jobExecution.getId());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("[Job Failed] jobName: {}", jobExecution.getJobInstance().getJobName());
        } else {
            log.info("[Job Success]");
        }
    }
}
