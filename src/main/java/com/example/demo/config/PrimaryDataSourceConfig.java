package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * 多数据源 主库数据源配置
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-03-20 17:32
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "PRIMARY_ENTITY_MANAGER_FACTORY",
        transactionManagerRef = "PRIMARY_PLATFORM_TX_MANAGER", basePackages = {"com.example.demo.repository.primary"})
public class PrimaryDataSourceConfig {

    public static final String PRIMARY_JPA_PROPS = "primary.jpa";
    public static final String PRIMARY_DATASOURCE = "primary.datasource";
    public static final String PRIMARY_PERSISTENCE_UNIT = "PRIMARY_PERSISTENCE_UNIT";
    public static final String PRIMARY_ENTITY_MANAGER = "PRIMARY_ENTITY_MANAGER";
    public static final String PRIMARY_ENTITY_MANAGER_FACTORY = "PRIMARY_ENTITY_MANAGER_FACTORY";
    public static final String PRIMARY_PLATFORM_TX_MANAGER = "PRIMARY_PLATFORM_TX_MANAGER";

    @Primary
    @Bean(name = PRIMARY_JPA_PROPS)
    @ConfigurationProperties(PRIMARY_JPA_PROPS)
    public JpaProperties primaryJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = PRIMARY_DATASOURCE)
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = PRIMARY_DATASOURCE)
    @ConfigurationProperties(prefix = PRIMARY_DATASOURCE)
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = PRIMARY_ENTITY_MANAGER)
    public EntityManager primaryEntityManager(@Qualifier(PRIMARY_ENTITY_MANAGER_FACTORY) EntityManagerFactory
                                                        entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Primary
    @Bean(name = PRIMARY_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource())
                .packages("com.example.demo.entity.primary")
                .persistenceUnit(PRIMARY_PERSISTENCE_UNIT)
                .properties(primaryJpaProperties().getProperties())
                .build();
    }

    @Primary
    @Bean(name = PRIMARY_PLATFORM_TX_MANAGER)
    public PlatformTransactionManager primaryPlatformTransactionManager(@Qualifier(PRIMARY_ENTITY_MANAGER_FACTORY)
                                                                                  EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
