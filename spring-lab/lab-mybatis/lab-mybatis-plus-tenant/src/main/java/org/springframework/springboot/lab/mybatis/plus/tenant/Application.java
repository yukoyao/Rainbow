package org.springframework.springboot.lab.mybatis.plus.tenant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author K
 */
@SpringBootApplication
@MapperScan(basePackages = "org.springframework.springboot.lab.mybatis.plus.tenant.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
