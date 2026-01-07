package com.example.springbatchdownversion.configuration.batch;

import com.example.springbatchdownversion.common.factory.CustomJobBuilderFactory;
import com.example.springbatchdownversion.domain.User;
import com.example.springbatchdownversion.infrastructure.batch.UserProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static com.example.springbatchdownversion.common.constants.BaseConstants.ENTITY_MANAGER_FACTORY;
import static com.example.springbatchdownversion.common.constants.BaseConstants.JPA_TX_MANAGER;


@Configuration
public class UserBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
//    private final ReaderFactory readerFactory;
    private final EntityManagerFactory entityManagerFactory;

    public UserBatchConfig(
            JobRepository jobRepository,
            @Qualifier(JPA_TX_MANAGER) PlatformTransactionManager transactionManager,
            @Qualifier(ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
        //this.readerFactory = readerFactory;
    }

    @Bean
    public Job sampleJob(CustomJobBuilderFactory factory, Step step) {
        return factory.get("sampleJob")
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

//    @Bean
//    @StepScope
//    public ItemReader<User> reader(
//            @Value("#{jobParameters['domainType']}") String domainType,
//            @Value("#{jobParameters['sourceType']}") String sourceType) {
//        ItemReader<User> reader = readerFactory.get(domainType, sourceType);
//        return reader;
//    }

    @Bean
    public ItemReader<User> reader() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("userItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .queryString("SELECT u FROM User u")
                .build();
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
