package org.rainbow.security.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainbow.security.dynamic.entity.Role;
import org.rainbow.security.dynamic.entity.User;
import java.util.List;

/**
 * @author K
 */
@Mapper
public interface UserMapper {

  List<Role> getUserRoleByUid(Integer uid);

  User loadUserByUsername(String username);

}
