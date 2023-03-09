package org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.dataobject.UserDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);
}
