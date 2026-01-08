package com.example.springbatchdownversion.common.factory;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.springbatchdownversion.common.constants.BaseConstants.JPA_TX_MANAGER;
import static com.example.springbatchdownversion.common.constants.BaseConstants.MYBATIS_TX_MANAGER;

public class CustomStepBuilderFactory {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager jpaTransactionManager;
    private final PlatformTransactionManager jdbcTransactionManager;

    public CustomStepBuilderFactory(JobRepository jobRepository,
                                    @Qualifier(JPA_TX_MANAGER) PlatformTransactionManager jpaTransactionManager,
                                    @Qualifier(MYBATIS_TX_MANAGER) PlatformTransactionManager jdbcTransactionManager) {
        this.jobRepository = jobRepository;
        this.jpaTransactionManager = jpaTransactionManager;
        this.jdbcTransactionManager = jdbcTransactionManager;

    }


    public StepBuilder get(String name) {
        return new StepBuilder(name, jobRepository);
    }

    // 자주 쓰는 설정을 미리 정의한 헬퍼 메서드 제공 가능
    public <I, O> SimpleStepBuilder<I, O> createJpaChunkStep(String name, int chunkSize,
                                                          Class<I> inputType, Class<O> outputType) {
        return new StepBuilder(name, jobRepository)
                .<I, O>chunk(chunkSize, jpaTransactionManager);
    }

    public <I, O> SimpleStepBuilder<I, O> createMybatisChunkStep(String name, int chunkSize,
                                                             Class<I> inputType, Class<O> outputType) {
        return new StepBuilder(name, jobRepository)
                .<I, O>chunk(chunkSize, jdbcTransactionManager);
    }
}
