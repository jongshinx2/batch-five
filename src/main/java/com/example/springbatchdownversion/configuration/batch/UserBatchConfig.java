package com.example.springbatchdownversion.configuration.batch;

import com.example.springbatchdownversion.common.factory.CustomJobBuilderFactory;
import com.example.springbatchdownversion.common.factory.CustomStepBuilderFactory;
import com.example.springbatchdownversion.domain.User;
import com.example.springbatchdownversion.infrastructure.batch.UserProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.springbatchdownversion.common.constants.BaseConstants.ENTITY_MANAGER_FACTORY;


@RequiredArgsConstructor
@Configuration
public class UserBatchConfig {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job sampleJob(CustomJobBuilderFactory factory, Step step) {
        return factory.get("sampleJob")
                .start(step)
                .build();
    }

    @Bean
    public Step userProcessingStep(ItemReader<User> reader,
                                   ItemProcessor<User, User> processor,
                                   ItemWriter<User> writer,
                                   CustomStepBuilderFactory factory) {
        return factory.createChunkStep("userProcessingStep", 10, User.class, User.class)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

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
