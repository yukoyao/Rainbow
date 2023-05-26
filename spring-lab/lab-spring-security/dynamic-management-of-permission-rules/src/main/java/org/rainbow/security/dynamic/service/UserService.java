package org.rainbow.security.dynamic.service;

import org.rainbow.security.dynamic.entity.User;
import org.rainbow.security.dynamic.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Component
public class UserService implements UserDetailsService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    User user = userMapper.loadUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    user.setRoles(userMapper.getUserRoleByUid(user.getId()));
    return user;
  }
}
