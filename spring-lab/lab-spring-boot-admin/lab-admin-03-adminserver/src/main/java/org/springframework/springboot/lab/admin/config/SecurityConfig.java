package org.springframework.springboot.lab.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author K
 */
@Configuration
@EnableWebSecurity // 开启 Security 对 WebFlux 的安全功能
public class SecurityConfig {

  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    // 创建一个用户
    UserDetails user = User.withUserDetails(User
        .withUsername("user").password("user").roles("USER").build()).build();

    return new MapReactiveUserDetailsService(user);
  }

  @Bean
  public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange(exchange ->
          exchange.pathMatchers("/assets/**").permitAll() // 静态资源, 允许匿名访问
              .pathMatchers("/login").permitAll()  // 登录接口, 允许匿名访问
              .anyExchange().authenticated()
        ).formLogin().loginPage("/login")  // 登录页面
        .and().logout().logoutUrl("/logout") // 登出页面
        .and().httpBasic() // HTTP Basic 认证方式
        .and().csrf().disable(); // csrf 禁用
    return http.build();
  }

}
