package org.springframework.springboot.lab.mybatis.annotation.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author K
 */
@Data
@Accessors(chain = true)
public class UserDO {

    private Integer id;

    private String username;

    private String password;

    private Date createTime;
}
