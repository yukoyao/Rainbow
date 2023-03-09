package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.dataobject;

import lombok.Data;
import lombok.ToString;

/**
 * 用户 DO
 *
 * @author K
 */
@Data
@ToString
public class UserDO {

    /**
     * 用户编号
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;
}
