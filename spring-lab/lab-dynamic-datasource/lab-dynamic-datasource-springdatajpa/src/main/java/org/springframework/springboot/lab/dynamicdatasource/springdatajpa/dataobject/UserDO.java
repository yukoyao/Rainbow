package org.springframework.springboot.lab.dynamicdatasource.springdatajpa.dataobject;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 用户 DO
 *
 * @author K
 */
@Data
@Entity
@ToString
@Table(name = "users")
public class UserDO {

    /**
     * 用户编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,  // strategy 设置使用数据库主键自增策略；
            generator = "JDBC") // generator 设置插入完成后，查询最后生成的 ID 填充到该属性中。
    private Integer id;
    /**
     * 账号
     */
    private String username;
}
