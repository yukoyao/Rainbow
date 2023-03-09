package org.springframework.springboot.lab.dynamicdatasource.mybatis.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.constant.DBConstants;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.dataobject.OrderDO;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.dataobject.UserDO;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.mapper.orders.OrderMapper;
import org.springframework.springboot.lab.dynamicdatasource.mybatis.mapper.users.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author K
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    private OrderService self() {
        return (OrderService) AopContext.currentProxy();
    }

    public void method01() {
        // 查询订单
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);

        // 查询用户
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Transactional // 报错，找不到事务管理器
    public void method02() {
        // 查询订单
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);

        // 查询用户
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    public void method03() {
        // 查询订单
        self().method01();
        // 查询用户
        self().method02();
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_ORDERS)
    public void method031() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_USERS)
    public void method032() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_ORDERS)
    public void method05() {
        // 查询订单
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
        // 查询用户
        self().method052();
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_USERS,
            propagation = Propagation.REQUIRES_NEW)
    public void method052() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }
}
