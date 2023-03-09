package org.springframework.springboot.lab.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.springboot.lab.mongodb.dataobject.ProductDO;

/**
 * @author K
 */
public interface ProductRepository extends MongoRepository<ProductDO, Integer> {
}
