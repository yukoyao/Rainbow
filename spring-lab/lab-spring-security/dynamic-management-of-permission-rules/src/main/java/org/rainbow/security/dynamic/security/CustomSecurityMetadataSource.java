package org.rainbow.security.dynamic.security;

import org.rainbow.security.dynamic.entity.Menu;
import org.rainbow.security.dynamic.entity.Role;
import org.rainbow.security.dynamic.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import java.util.Collection;
import java.util.List;

/**
 * @author K
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

  @Autowired
  private MenuService menuService;

  AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public Collection<ConfigAttribute> getAttributes(final Object object) throws IllegalArgumentException {
    String requestURI = ((FilterInvocation) object).getRequest().getRequestURI();
    List<Menu> allMenu = menuService.getAllMenu();
    for (Menu menu : allMenu) {
      if (antPathMatcher.match(menu.getPattern(), requestURI)) {
        String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
        return SecurityConfig.createList(roles);
      }
    }
    return null;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(final Class<?> clazz) {
    return FilterInvocation.class.isAssignableFrom(clazz);
  }
}
