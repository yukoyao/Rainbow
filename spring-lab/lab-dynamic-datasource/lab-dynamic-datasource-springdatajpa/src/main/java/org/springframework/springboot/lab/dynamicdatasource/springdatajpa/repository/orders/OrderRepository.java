package org.springframework.springboot.lab.dynamicdatasource.springdatajpa.repository.orders;

import org.springframework.data.repository.CrudRepository;
import org.springframework.springboot.lab.dynamicdatasource.springdatajpa.dataobject.OrderDO;

/**
 * @author K
 */
public interface OrderRepository extends CrudRepository<OrderDO, Integer> {
}
