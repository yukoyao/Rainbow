package org.springframework.springboot.lab.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.springboot.lab.elasticsearch.dataobject.ESProductDO;

/**
 * @author K
 */
public interface ProductRepository extends ElasticsearchRepository<ESProductDO, Integer> {
}
