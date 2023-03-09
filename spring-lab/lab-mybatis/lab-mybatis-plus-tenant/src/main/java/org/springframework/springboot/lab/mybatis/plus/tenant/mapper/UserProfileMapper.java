package org.springframework.springboot.lab.mybatis.plus.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.springboot.lab.mybatis.plus.tenant.dataobject.UserProfileDO;
import org.springframework.stereotype.Repository;

/**
 * @author K
 */
@Repository
public interface UserProfileMapper extends BaseMapper<UserProfileDO> {
}
