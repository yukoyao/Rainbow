package org.springframework.springboot.lab.mybatis.annotation.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.springboot.lab.mybatis.annotation.dataobject.UserDO;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author K
 */
@Repository
public interface UserMapper {

    @Insert("INSERT INTO users(username, password, create_time) VALUES(#{username}, #{password}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(UserDO user);

    @Update(value = {
            "<script>",
            "UPDATE users",
            "<set>",
            "<if test='username != null'>, username = #{username}</if>",
            "<if test='password != null'>, password = #{password}</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    int updateById(UserDO user);

    @Insert("DELETE FROM users where id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT username, password, create_time FROM users WHERE id = #{id}")
    UserDO selectById(@Param("id") Integer id);

    @Select("SELECT username, password, create_time FROM users WHERE username = #{username}")
    UserDO selectByUsername(@Param("username") String username);

    @Select(value = {
            "<script>",
            "SELECT username, password, create_time FROM users",
            "WHERE id IN",
            "<foreach item='id' collection='ids' separator=',' open='(' close=')' index=''>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<UserDO> selectByIds(@Param("ids") Collection<Integer> ids);
}
