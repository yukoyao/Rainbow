package org.springframework.springboot.lab.dynamicdatasource.baomidou.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.constant.DBConstants;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.dataobject.UserDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
@DS(DBConstants.DATASOURCE_USERS)
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);
}
