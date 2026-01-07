package com.example.springbatchdownversion.common.jobs;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BatchJobExecutor {

    private final JobLauncher jobLauncher;

    /**
     * 배치를 실행하는 공통 메서드
     * (넘겨받은 파라미터가 있으면 합치고, 없으면 새로 만듭니다)
     *
     * @param job 실행할 Job 객체
     * @param jobParameters 추가할 파라미터 (없으면 null 가능)
     */
    public void execute(Job job, JobParameters jobParameters) {
        try {

            JobParameters finalParameters = new JobParametersBuilder(jobParameters != null ? jobParameters : new JobParameters())
                    .addLong("run.timestamp", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, finalParameters);

            log.info("Job: {} executed. Status: {}", job.getName(), execution.getStatus());

        } catch (JobInstanceAlreadyCompleteException e) {
            log.warn("Job: {} is already completed.", job.getName());
        } catch (Exception e) {
            log.error("Job: {} failed. Error: {}", job.getName(), e.getMessage(), e);
        }
    }
}
