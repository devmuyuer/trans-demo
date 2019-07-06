package com.example.demo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * 这里主要是开启mongo事务的配置 若不需要启用mongo事务则不需要这个config
 * @author muyuer 182443947@qq.com
 * @version 1.0
 * @date 2019-07-03 08:50
 */

@Configuration
public class MongoConfig extends AbstractMongoConfiguration{

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    @Value("${spring.data.mongodb.database}")
    private String mongoDataBaseName;

    @Bean(name = "MONGO_TRANSACTION_MANAGER")
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDataBaseName;
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientURI uri = new MongoClientURI(mongoUri);
        return new MongoClient(uri);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
                                                       MongoMappingContext context, BeanFactory beanFactory){
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver,context);
        try{
            converter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        //don't save column _class to mongo collection
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}
