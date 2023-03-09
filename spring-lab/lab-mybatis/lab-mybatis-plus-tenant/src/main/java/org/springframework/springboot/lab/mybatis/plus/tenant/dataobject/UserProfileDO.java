package org.springframework.springboot.lab.mybatis.plus.tenant.dataobject;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户拓展 DO
 *
 * @author K
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_profile")
public class UserProfileDO {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 性别
     */
    private Integer gender;
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
