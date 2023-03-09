package org.springframework.springboot.lab.dynamicdatasource.mybatis.mapper.users;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.dataobject.UserDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);
}
