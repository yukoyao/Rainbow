package org.springframework.springboot.lab.mongodb.dataobject;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户 DO
 *
 * @author K
 */
@Data
@ToString
@Accessors(chain = true)
@Document(collection = "User")
public class UserDO {

    @Id
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 创建是啊金
     */
    private String password;

    /**
     * 用户信息
     */
    private Profile profile;

    /**
     * 用户信息
     */
    @Data
    @ToString
    @Accessors(chain = true)
    public static class Profile {

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 性别
         */
        private Integer gender;
    }
}
