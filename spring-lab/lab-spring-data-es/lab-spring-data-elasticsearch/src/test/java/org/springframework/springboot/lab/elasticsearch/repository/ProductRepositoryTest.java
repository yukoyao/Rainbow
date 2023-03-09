package org.springframework.springboot.lab.elasticsearch.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.elasticsearch.Application;
import org.springframework.springboot.lab.elasticsearch.dataobject.ESProductDO;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

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
        ESProductDO product = new ESProductDO();
        product.setId(1);
        product.setName("可爱小嘎嘎");
        product.setSellPoint("可爱");
        product.setDescription("只是一头小嘎嘎");
        product.setCid(1);
        product.setCategoryName("玩偶");
        productRepository.save(product);
    }

    @Test
    public void testUpdate() {
        Optional<ESProductDO> optional = productRepository.findById(1);
        if (optional.isPresent()) {
            ESProductDO esProductDO = optional.get();
            esProductDO.setName("可爱小嘎嘎~");
            productRepository.save(esProductDO);
        }
    }

    @Test
    public void testDelete() {
        productRepository.deleteById(1);
    }

    @Test
    public void testSelectById() {
        Optional<ESProductDO> productDO = productRepository.findById(1);
        System.out.println(productDO.get());
    }

    @Test
    public void testSelectByIds() {
        Iterable<ESProductDO> iterable = productRepository.findAllById(Arrays.asList(1, 4));
        iterable.forEach(System.out::println);
    }
}
