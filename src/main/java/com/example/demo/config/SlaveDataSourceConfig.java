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
 * 多数据源 从数据源配置
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-03-20 17:32
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "SLAVE_ENTITY_MANAGER_FACTORY",
        transactionManagerRef = "SLAVE_PLATFORM_TX_MANAGER", basePackages = {"com.example.demo.repository.slave"})
public class SlaveDataSourceConfig {

    public static final String SLAVE_JPA_PROPS = "slave.jpa";
    public static final String SLAVE_DATASOURCE = "slave.datasource";
    public static final String SLAVE_PERSISTENCE_UNIT = "SLAVE_PERSISTENCE_UNIT";
    public static final String SLAVE_ENTITY_MANAGER = "SLAVE_ENTITY_MANAGER";
    public static final String SLAVE_ENTITY_MANAGER_FACTORY = "SLAVE_ENTITY_MANAGER_FACTORY";
    public static final String SLAVE_PLATFORM_TX_MANAGER = "SLAVE_PLATFORM_TX_MANAGER";

    @Bean(name = SLAVE_JPA_PROPS)
    @ConfigurationProperties(SLAVE_JPA_PROPS)
    public JpaProperties slaveJpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = SLAVE_DATASOURCE)
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = SLAVE_DATASOURCE)
    @ConfigurationProperties(prefix = SLAVE_DATASOURCE)
    public DataSource slaveDataSource() {
        return slaveDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = SLAVE_ENTITY_MANAGER)
    public EntityManager slaveEntityManager(@Qualifier(SLAVE_ENTITY_MANAGER_FACTORY) EntityManagerFactory
                                                        entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean(name = SLAVE_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(slaveDataSource())
                .packages("com.example.demo.entity.slave")
                .persistenceUnit(SLAVE_PERSISTENCE_UNIT)
                .properties(slaveJpaProperties().getProperties())
                .build();
    }

    @Bean(name = SLAVE_PLATFORM_TX_MANAGER)
    public PlatformTransactionManager slavePlatformTransactionManager(@Qualifier(SLAVE_ENTITY_MANAGER_FACTORY)
                                                                                  EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
