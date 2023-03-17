package org.rainbow.kill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author K
 */
@EnableKafka
@SpringBootApplication
@EnableTransactionManagement
public class SecondsKillApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecondsKillApplication.class, args);
  }

}
