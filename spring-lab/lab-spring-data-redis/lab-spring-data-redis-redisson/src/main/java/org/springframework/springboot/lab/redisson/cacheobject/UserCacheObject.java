package org.springframework.springboot.lab.redisson.cacheobject;

/**
 * 用户缓存对象
 *
 * @author K
 */
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
