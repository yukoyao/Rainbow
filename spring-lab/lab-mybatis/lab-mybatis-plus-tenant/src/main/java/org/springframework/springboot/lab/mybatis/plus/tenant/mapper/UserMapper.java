package org.springframework.springboot.lab.mybatis.plus.tenant.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.springboot.lab.mybatis.plus.tenant.dataobject.UserDO;
import org.springframework.springboot.lab.mybatis.plus.tenant.vo.UserDetailVO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author K
 */
@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    default UserDO selectByUsername(@Param("username") String username) {
        return selectOne(new QueryWrapper<UserDO>().eq("username", username));
    }

    List<UserDO> selectByIds(@Param("ids") Collection<Integer> ids);

    default IPage<UserDO> selectPageByCreateTime(IPage<UserDO> page, @Param("createTime")Date creatTime) {
        return selectPage(page, new QueryWrapper<UserDO>().gt("create_time", creatTime));
    }

    List<UserDetailVO> selectListA();

    List<UserDetailVO> selectListB();

}
