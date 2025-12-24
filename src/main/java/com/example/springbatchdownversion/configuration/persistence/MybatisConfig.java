package com.example.springbatchdownversion.configuration.persistence;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static com.example.springbatchdownversion.common.constants.BaseConstants.*;
import static org.apache.ibatis.session.ExecutorType.SIMPLE;

@MapperScan(
        basePackages = MYBATIS_BASE_PACKAGE,
        sqlSessionFactoryRef = SESSION_FACTORY,
        sqlSessionTemplateRef = SESSION_TEMPLATE
)
@Configuration
public class MybatisConfig extends PersistenceConfig {

    @Primary
    @Bean(SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(primaryDataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage(MYBATIS_ALIAS_PACKAGE);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MYBATIS_MAPPER_LOCATION));
        sqlSessionFactoryBean.setConfiguration(getConfiguration());
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(SESSION_TEMPLATE)
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory, SIMPLE);
    }

    @Primary
    @Bean(MYBATIS_TX_MANAGER)
    public PlatformTransactionManager mybatisTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    private org.apache.ibatis.session.Configuration getConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setDefaultExecutorType(SIMPLE);
        configuration.setCacheEnabled(false);
        configuration.setAggressiveLazyLoading(false);
        configuration.setDefaultEnumTypeHandler(EnumTypeHandler.class);
        return configuration;
    }
}
