package org.springframework.springboot.lab.tomcat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author K
 */
@RestController
public class Controller {

    @GetMapping("/hello")
    public String hello() {
        return "world";
    }
}
