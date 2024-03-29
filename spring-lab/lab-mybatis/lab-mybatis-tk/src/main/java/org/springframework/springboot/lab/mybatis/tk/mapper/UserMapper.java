package org.springframework.springboot.lab.mybatis.tk.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.mybatis.tk.dataobject.UserDO;
import org.springframework.springboot.lab.mybatis.tk.util.BaseMapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author K
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    default UserDO selectByUsername(@Param("username") String username) {
        Example example = new Example(UserDO.class);
        // 创建 Criteria 对象, 设置 username 查询条件
        example.createCriteria().andEqualTo("username", username);
        // 执行查询
        return selectOneByExample(example);
    }

    List<UserDO> selectByIds(@Param("ids") Collection<Integer> ids);

    default List<UserDO> selectListByCreateTime(@Param("createTime") Date createTime) {
        Example example = new Example(UserDO.class);
        // 创建 Criteria 对象，设置 create_time 查询条件
        example.createCriteria().andGreaterThan("createTime", createTime);
        return selectByExample(example);
    }
}
