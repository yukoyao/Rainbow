package org.rainbow.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.Arrays;

/**
 * @author K
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  AuthenticationProvider kaptchaAuthenticationProvider() {
    InMemoryUserDetailsManager users = new InMemoryUserDetailsManager(User.builder()
        .username("admin").password("{noop}123456").roles("admin").build());
    KaptchaAuthenticationProvider provider = new KaptchaAuthenticationProvider();
    provider.setUserDetailsService(users);
    return provider;
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    ProviderManager manager = new ProviderManager(Arrays.asList(kaptchaAuthenticationProvider()));
    return manager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/vc.jpg").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login.html")
        .loginProcessingUrl("/doLogin")
        .defaultSuccessUrl("/index.html")
        .failureForwardUrl("/login.html")
        .usernameParameter("uname")
        .passwordParameter("passwd")
        .permitAll()
        .and()
        .csrf().disable();
  }
}
