package org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.dataobject;

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

    private Integer id;

    private String username;
}
