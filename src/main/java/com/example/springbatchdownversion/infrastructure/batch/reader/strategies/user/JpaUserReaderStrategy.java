package com.example.springbatchdownversion.infrastructure.batch.reader.strategies.user;

import com.example.springbatchdownversion.common.constants.DomainType;
import com.example.springbatchdownversion.common.constants.SourceType;
import com.example.springbatchdownversion.domain.User;
import com.example.springbatchdownversion.infrastructure.batch.reader.strategies.ReaderStrategy;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.example.springbatchdownversion.common.constants.BaseConstants.ENTITY_MANAGER_FACTORY;

@Component
public class JpaUserReaderStrategy implements ReaderStrategy<User> {

    private final EntityManagerFactory emf;

    public JpaUserReaderStrategy(
            @Qualifier(ENTITY_MANAGER_FACTORY) EntityManagerFactory emf
    ) {
        this.emf = emf;
    }

    @Override
    public String getDomainType() {
        return DomainType.USER.getKey();
    }

    @Override
    public String getSourceType() {
        return SourceType.JPA.getKey();
    }

    @Override
    public ItemReader<User> create() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("jpaUserReader")
                .entityManagerFactory(emf)
                .queryString("SELECT u FROM User u")
                .pageSize(100)
                .build();
    }
}
