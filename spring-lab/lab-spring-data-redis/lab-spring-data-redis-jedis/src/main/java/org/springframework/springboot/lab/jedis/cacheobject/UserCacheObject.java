package org.springframework.springboot.lab.jedis.cacheobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户缓存对象
 *
 * @author K
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheObject {

    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    @Override
    public String toString() {
        return "UserCacheObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
