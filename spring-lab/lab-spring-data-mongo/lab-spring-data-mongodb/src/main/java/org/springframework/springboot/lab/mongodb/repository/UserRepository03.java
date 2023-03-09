package org.springframework.springboot.lab.mongodb.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.springboot.lab.mongodb.dataobject.UserDO;

/**
 * @author K
 */
public interface UserRepository03 extends MongoRepository<UserDO, Integer> {

    // 使用 Username 精准匹配
    default UserDO findByUsername01(String username) {
        // 创建 Example 对象, 使用 username 查询
        UserDO probe = new UserDO();
        probe.setUsername(username);
        Example<UserDO> example = Example.of(probe);
        return findOne(example).orElse(null);
    }

    // 使用 Username 模糊匹配
    default UserDO findByUsernameLike01(String username) {
        UserDO probe = new UserDO();
        probe.setUsername(username);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<UserDO> example = Example.of(probe, matcher);
        return findOne(example).orElse(null);
    }
}
