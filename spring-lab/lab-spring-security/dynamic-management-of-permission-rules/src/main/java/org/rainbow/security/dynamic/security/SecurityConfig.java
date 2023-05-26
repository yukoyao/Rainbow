package org.rainbow.security.dynamic.security;

import org.rainbow.security.dynamic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;


/**
 * @author K
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomSecurityMetadataSource customSecurityMetadataSource;

  @Autowired
  UserService userService;

  @Autowired
  CustomAccessDeniedHandler customAccessDeniedHandler;



  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
    http.apply(new UrlAuthorizationConfigurer<>(applicationContext)).withObjectPostProcessor(
        new ObjectPostProcessor<FilterSecurityInterceptor>() {
          @Override
          public <O extends FilterSecurityInterceptor> O postProcess(final O o) {
            o.setSecurityMetadataSource(customSecurityMetadataSource);
            return o;
          }
        });
    http.formLogin().and().csrf().disable().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
  }
}
