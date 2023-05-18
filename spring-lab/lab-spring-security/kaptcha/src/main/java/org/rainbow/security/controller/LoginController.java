package org.rainbow.security.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author K
 */
@Controller
public class LoginController {

  @Autowired
  Producer producer;


  @GetMapping("/vc.jpg")
  public void getVerifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
    resp.setContentType("image/jpeg");
    String text = producer.createText();
    session.setAttribute("kaptcha", text);
    BufferedImage image = producer.createImage(text);
    try (ServletOutputStream out = resp.getOutputStream()) {
      ImageIO.write(image, "jpg", out);
    }
  }

  @RequestMapping("/index.html")
  public String index() {
    return "index";
  }

  @RequestMapping("/hello")
  public String hello() {
    return "hello spring security";
  }


  @RequestMapping("/login.html")
  public String login() {
    return "login";
  }
}
