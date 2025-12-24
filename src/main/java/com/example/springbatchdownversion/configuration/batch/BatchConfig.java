//package com.example.springbatchdownversion.configuration.batch;
//
//import com.example.springbatchdownversion.common.constants.DomainType;
//import com.example.springbatchdownversion.common.constants.SourceType;
//import com.example.springbatchdownversion.domain.User;
//import com.example.springbatchdownversion.infrastructure.batch.UserProcessor;
//import com.example.springbatchdownversion.infrastructure.batch.reader.ReaderFactory;
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JpaItemWriter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@RequiredArgsConstructor
//@Configuration
//public class BatchConfig {
//
//    private final JobRepository jobRepository;
//    private final EntityManagerFactory entityManagerFactory;
//    private final PlatformTransactionManager transactionManager;
//    private final ReaderFactory readerFactory;
//
//    @Bean
//    public Job sampleJob(Step step) {
//        return new JobBuilder("sampleJob", jobRepository)
//                .start(step)
//                .build();
//    }
//
//    @Bean
//    public Step userProcessingstep(ItemReader<User> reader,
//                                   ItemProcessor<User, User> processor,
//                                   ItemWriter<User> writer) {
//        return new StepBuilder("userProcessingStep", jobRepository)
//                .<User, User>chunk(10, transactionManager)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public ItemReader<User> reader(@Value("#{jobParameters['sourceType']}") String sourceType) {
//        return readerFactory.get(DomainType.USER.getKey(), sourceType);
//    }
//
//    @Bean
//    public UserProcessor processor() {
//        return new UserProcessor();
//    }
//
//    @Bean
//    public JpaItemWriter<User> writer() {
//        JpaItemWriter<User> writer = new JpaItemWriter<>();
//        writer.setEntityManagerFactory(entityManagerFactory);
//        return writer;
//    }
//}
