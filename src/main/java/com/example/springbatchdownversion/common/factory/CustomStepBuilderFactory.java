package com.example.springbatchdownversion.common.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
public class CustomStepBuilderFactory {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public StepBuilder get(String name) {
        return new StepBuilder(name, jobRepository);
    }

    // 자주 쓰는 설정을 미리 정의한 헬퍼 메서드 제공 가능
    public <I, O> SimpleStepBuilder<I, O> createChunkStep(String name, int chunkSize) {
        return new StepBuilder(name, jobRepository)
                .<I, O>chunk(chunkSize, transactionManager)
                .faultTolerant()
                .retryLimit(3);
    }
}
