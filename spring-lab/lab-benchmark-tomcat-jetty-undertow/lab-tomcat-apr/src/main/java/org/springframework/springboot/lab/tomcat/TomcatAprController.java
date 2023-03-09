package org.springframework.springboot.lab.tomcat;

import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author K
 */
@SpringBootApplication
public class TomcatAprController {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        factory.addContextLifecycleListeners(new AprLifecycleListener());
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(TomcatAprController.class, args);
    }
}
