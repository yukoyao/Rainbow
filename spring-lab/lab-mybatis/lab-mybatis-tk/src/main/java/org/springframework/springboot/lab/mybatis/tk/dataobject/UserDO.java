package org.springframework.springboot.lab.mybatis.tk.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author K
 */
@Data
@Accessors(chain = true)
@Table(name = "users")
public class UserDO {

    @Id // 表示该字段为主键 ID
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC") // strategy 设置使用数据库主键自增策略；generator 设置插入完成后，查询最后生成的 ID 填充到该属性中。
    private Integer id;

    private String username;

    private String password;

    private Date createTime;


}
