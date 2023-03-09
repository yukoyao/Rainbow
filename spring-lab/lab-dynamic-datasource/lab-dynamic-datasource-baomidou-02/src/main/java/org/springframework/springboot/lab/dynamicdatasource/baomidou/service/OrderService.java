package org.springframework.springboot.lab.dynamicdatasource.baomidou.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.constant.DBConstants;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.dataobject.OrderDO;
import org.springframework.springboot.lab.dynamicdatasource.baomidou.mapper.OrderMapper;
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
    @DS(DBConstants.DATASOURCE_MASTER)
    public void add(OrderDO order) {
        OrderDO exists = orderMapper.selectById(order.getId());
        System.out.println(exists);

        // 插入订单
        orderMapper.insert(order);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }
}
