package org.springframework.springboot.lab.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author K
 */
@SpringBootApplication
@MapperScan(basePackages = "org.springframework.springboot.lab.sharding.mapper")
public class Application {
}
