package com.example.springbatchdownversion.configuration;

import com.example.springbatchdownversion.common.factory.CustomJobBuilderFactory;
import com.example.springbatchdownversion.common.factory.CustomStepBuilderFactory;
import com.example.springbatchdownversion.common.jobs.GlobalJobListener;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.springbatchdownversion.common.constants.BaseConstants.JPA_TX_MANAGER;

@Configuration
@ConditionalOnClass(JobLauncher.class)
public class BatchCommonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JobExecutionListener jobExecutionListener() {
        return new GlobalJobListener();
    }

    @Bean
    public CustomJobBuilderFactory jobBuilderFactory(
            JobRepository jobRepository,
            JobExecutionListener globalJobListener) {

        return new CustomJobBuilderFactory(jobRepository, globalJobListener);
    }

    @Bean
    public CustomStepBuilderFactory stepBuilderFactory(
            JobRepository jobRepository,
            @Qualifier(JPA_TX_MANAGER) PlatformTransactionManager transactionManager) {

        return new CustomStepBuilderFactory(jobRepository, transactionManager);
    }

}
