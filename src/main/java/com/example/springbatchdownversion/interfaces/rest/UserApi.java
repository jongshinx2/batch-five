package com.example.springbatchdownversion.interfaces.rest;

import com.example.springbatchdownversion.common.constants.DomainType;
import com.example.springbatchdownversion.common.constants.SourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserApi {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping("/start-batch")
    public String startBatch() {
        try {
            JobExecution execution = jobLauncher.run(job,
                    new JobParametersBuilder()
                            .addString("domainType", DomainType.USER.getKey())
                            .addString("sourceType", SourceType.JPA.getKey())
                            .toJobParameters());

            return "Batch job has been invoked. Status: " + execution.getStatus();
        } catch (Exception e) {
            return "Failed to invoke batch job: " + e.getMessage();
        }
    }
}
