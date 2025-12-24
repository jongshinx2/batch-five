package com.example.springbatchdownversion.configuration.persistence;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.example.springbatchdownversion.common.constants.BaseConstants.BASE_DATASOURCE;
import static com.example.springbatchdownversion.common.constants.BaseConstants.DATASOURCE;

@EnableTransactionManagement
//@Configuration
public class PersistenceConfig {

    @Primary
    @Bean(DATASOURCE)
    protected DataSource primaryDataSource() {
        DataSource dataSource = baseDataSource();
        return new LazyConnectionDataSourceProxy(dataSource);
    }

    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Bean(BASE_DATASOURCE)
    protected DataSource baseDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
