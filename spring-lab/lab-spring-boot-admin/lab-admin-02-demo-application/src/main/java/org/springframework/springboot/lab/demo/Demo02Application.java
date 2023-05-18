package org.springframework.springboot.lab.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author K
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Demo02Application {

  public static void main(String[] args) {
    System.setProperty("server.port", "18002");
    SpringApplication.run(Demo02Application.class, args);
  }
}
