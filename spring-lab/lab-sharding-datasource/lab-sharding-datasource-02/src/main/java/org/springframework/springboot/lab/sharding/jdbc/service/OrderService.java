package org.springframework.springboot.lab.sharding.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.springboot.lab.sharding.jdbc.dataobject.OrderDO;
import org.springframework.springboot.lab.sharding.jdbc.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author K
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public void add(OrderDO order) {
        OrderDO exists = orderMapper.selectById(1);
        System.out.println(exists);

        orderMapper.insert(exists);

        exists = orderMapper.selectById(1);
        System.out.println(exists);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }
}
