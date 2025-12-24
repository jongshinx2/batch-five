package com.example.springbatchdownversion.configuration.batch.user;

import com.example.springbatchdownversion.common.constants.DomainType;
import com.example.springbatchdownversion.common.constants.SourceType;
import com.example.springbatchdownversion.domain.User;
import com.example.springbatchdownversion.infrastructure.batch.UserProcessor;
import com.example.springbatchdownversion.infrastructure.batch.reader.ReaderFactory;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.springbatchdownversion.common.constants.BaseConstants.ENTITY_MANAGER_FACTORY;
import static com.example.springbatchdownversion.common.constants.BaseConstants.JPA_TX_MANAGER;


@Configuration
public class UserBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ReaderFactory readerFactory;

    public UserBatchConfig(
            JobRepository jobRepository,
            @Qualifier(JPA_TX_MANAGER) PlatformTransactionManager transactionManager,
            ReaderFactory readerFactory
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.readerFactory = readerFactory;
    }

    @Bean
    public Job sampleJob(Step step) {
        return new JobBuilder("sampleJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step userProcessingstep(ItemReader<User> reader,
                                   ItemProcessor<User, User> processor,
                                   ItemWriter<User> writer) {
        return new StepBuilder("userProcessingStep", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<User> reader(
            @Value("#{jobParameters['domainType']}") String domainType,
            @Value("#{jobParameters['sourceType']}") String sourceType) {
        ItemReader<User> reader = readerFactory.get(domainType, sourceType);
        return reader;
    }

    @Bean
    public UserProcessor processor() {
        return new UserProcessor();
    }

    @Bean
    public JpaItemWriter<User> writer(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
