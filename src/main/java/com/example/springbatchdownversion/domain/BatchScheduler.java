package com.example.springbatchdownversion.domain;

import com.example.springbatchdownversion.common.jobs.BatchJobExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BatchScheduler {

    private final BatchJobExecutor batchJobExecutor;

    private final Job sampleJob;

    @Scheduled(cron = "0 */1 * * * *")
    public void runSampleJob() {
        batchJobExecutor.execute(sampleJob, null);
    }

}
