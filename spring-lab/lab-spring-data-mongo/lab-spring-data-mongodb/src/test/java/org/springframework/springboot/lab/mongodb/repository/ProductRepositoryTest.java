package org.springframework.springboot.lab.mongodb.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.mongodb.Application;
import org.springframework.springboot.lab.mongodb.dataobject.ProductDO;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert() {
        ProductDO productDO = new ProductDO();
        productDO.setName("番薯");
        productRepository.insert(productDO);
        System.out.println(productDO.getId());
    }
}
