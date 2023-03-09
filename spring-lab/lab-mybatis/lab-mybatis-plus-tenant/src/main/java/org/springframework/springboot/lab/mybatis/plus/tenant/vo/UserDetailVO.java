package org.springframework.springboot.lab.mybatis.plus.tenant.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author K
 */
@Data
@Accessors(chain = true)
public class UserDetailVO {

    /**
     * 用户编号
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码（明文）
     *
     * ps：生产环境下，千万不要明文噢
     */
    private String password;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
    /**
     * 租户编号
     */
    private Integer tenantId;
}
