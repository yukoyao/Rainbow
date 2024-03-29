package org.springframework.springboot.lab.dynamicdatasource.springdatajpa.service;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.constant.DBConstants;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.dataobject.OrderDO;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.dataobject.UserDO;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.repository.orders.OrderRepository;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.repository.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author K
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private OrderService self() {
        return (OrderService) AopContext.currentProxy();
    }

    public void method01() {
        // 查询订单
        OrderDO order = orderRepository.findById(1).orElse(null);
        System.out.println(order);
        // 查询用户
        UserDO user = userRepository.findById(1).orElse(null);
        System.out.println(user);
    }

    @Transactional // 报错，找不到事务管理器
    public void method02() {
        // 查询订单
        OrderDO order = orderRepository.findById(1).orElse(null);
        System.out.println(order);
        // 查询用户
        UserDO user = userRepository.findById(1).orElse(null);
        System.out.println(user);
    }

    public void method03() {
        // 查询订单
        self().method031();
        // 查询用户
        self().method032();
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_ORDERS)
    public void method031() {
        OrderDO order = orderRepository.findById(1).orElse(null);
        System.out.println(order);
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_USERS)
    public void method032() {
        UserDO user = userRepository.findById(1).orElse(null);
        System.out.println(user);
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_ORDERS)
    public void method05() {
        // 查询订单
        OrderDO order = orderRepository.findById(1).orElse(null);
        System.out.println(order);
        // 查询用户
        self().method052();
    }

    @Transactional(transactionManager = DBConstants.TX_MANAGER_USERS,
            propagation = Propagation.REQUIRES_NEW)
    public void method052() {
        UserDO user = userRepository.findById(1).orElse(null);
        System.out.println(user);
    }
}
