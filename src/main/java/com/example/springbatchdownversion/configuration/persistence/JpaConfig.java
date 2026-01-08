package com.example.springbatchdownversion.configuration.persistence;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

import static com.example.springbatchdownversion.common.constants.BaseConstants.*;

@RequiredArgsConstructor
@EnableJpaRepositories(
        basePackages = { JPA_BASE_PACKAGE },
        entityManagerFactoryRef = ENTITY_MANAGER_FACTORY,
        transactionManagerRef = JPA_TX_MANAGER
)
@Configuration
public class JpaConfig extends PersistenceConfig {

    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    private JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        return adapter;
    }

    @Primary
    @Bean(ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPersistenceUnitName(JPA_PERSISTENCE_UNIT);
        emf.setJpaPropertyMap(jpaProperties.getProperties());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setDataSource(primaryDataSource());
        emf.setPackagesToScan(JPA_ENTITY_PACKAGE);

        Map<String, Object> properties =
                hibernateProperties.determineHibernateProperties(
                        jpaProperties.getProperties(),
                        new HibernateSettings()
                );

        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean(JPA_TX_MANAGER)
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
