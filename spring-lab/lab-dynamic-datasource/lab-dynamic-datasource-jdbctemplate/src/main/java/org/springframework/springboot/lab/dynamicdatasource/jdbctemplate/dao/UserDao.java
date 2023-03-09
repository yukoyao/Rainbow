package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.constant.DBConstants;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dataobject.UserDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author K
 */
@Repository
public class UserDao {

    @Resource(name = DBConstants.JDBC_TEMPLATE_USERS)
    private JdbcTemplate template;

    public UserDO selectById(Integer id) {
        return template.queryForObject("SELECT id, username FROM users WHERE id = ?",
                new BeanPropertyRowMapper<>(UserDO.class), // 结果转换成对应的对象
                id);
    }
}
