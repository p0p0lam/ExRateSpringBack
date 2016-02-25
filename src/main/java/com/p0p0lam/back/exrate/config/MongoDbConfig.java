package com.p0p0lam.back.exrate.config;

import com.mongodb.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * Created by Sergey on 22.02.2016.
 */
@Configuration
@EnableMongoRepositories("com.p0p0lam.back.exrate.repository")
public class MongoDbConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return System.getenv("OPENSHIFT_APP_NAME");
    }

    @Override
    public Mongo mongo() throws Exception {
        String openshiftMongoDbHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        MongoCredential credential = MongoCredential.createCredential(username,
                getDatabaseName(),
                password.toCharArray());
        MongoClient client = new MongoClient(new ServerAddress(openshiftMongoDbHost, openshiftMongoDbPort), Arrays.asList(credential));
        return client;
    }

    @Override
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory((MongoClient)mongo(), getDatabaseName());
    }

    /*
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String openshiftMongoDbHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
        //MongoClient client = new MongoClient(openshiftMongoDbHost, openshiftMongoDbPort);
        Mongo mongo = new Mongo(openshiftMongoDbHost, openshiftMongoDbPort);
        UserCredentials userCredentials = new UserCredentials(username, password);
        String databaseName = System.getenv("OPENSHIFT_APP_NAME");
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
    */
}
