package org.springframework.springboot.lab.jpa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.springboot.lab.jpa.dataobject.UserDO;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepository02Test {

    @Autowired
    private UserRepository02 userRepository;

    @Test
    public void testFindAll() {
        // 创建排序条件
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // 执行排序条件
        Iterable<UserDO> iterable = userRepository.findAll(sort);
        iterable.forEach(System.out::println);
    }

    @Test
    public void testFindPage() {
        // 创建排序条件
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // 创建分页条件
        Pageable pageable = PageRequest.of(1, 10, sort);
        // 执行分页操作
        Page<UserDO> page = userRepository.findAll(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
    }
}
