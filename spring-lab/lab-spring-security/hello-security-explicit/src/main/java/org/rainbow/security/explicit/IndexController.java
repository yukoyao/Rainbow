package org.rainbow.security.explicit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author K
 */
@Controller
public class IndexController {

  @GetMapping("/")
  public String index() {
    return "index";
  }
}
