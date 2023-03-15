package org.rainbow.tokens.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author K
 */
@Configuration
public class MongoDBConfig {

  @Value("${spring.data.mongodb.uri}")
  private String mongoDbUri;

  @Value("${spring.data.mongodb.database}")
  private String databaseName;

  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(mongoDbUri);
  }


  @Bean
  public MongoTemplate mongoTemplate(MongoClient client) {
    return new MongoTemplate(client, databaseName);
  }

  @Bean
  MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
    return new MongoTransactionManager(dbFactory);
  }

}
