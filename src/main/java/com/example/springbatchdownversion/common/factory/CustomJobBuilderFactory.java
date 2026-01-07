package com.example.springbatchdownversion.common.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

@RequiredArgsConstructor
public class CustomJobBuilderFactory {

    private final JobRepository jobRepository;
    private final JobExecutionListener globalJobListener;

    public JobBuilder get(String name){
        return new JobBuilder(name, jobRepository)
                .listener(globalJobListener)
                .incrementer(new RunIdIncrementer());
    }
}
