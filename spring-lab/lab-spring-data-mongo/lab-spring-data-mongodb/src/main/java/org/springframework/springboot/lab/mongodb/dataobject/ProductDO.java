package org.springframework.springboot.lab.mongodb.dataobject;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.springboot.lab.mongodb.mongo.IncIdEntity;

/**
 * 商品 DO
 *
 * @author K
 */
@Data
@Document(collection = "Product")
public class ProductDO extends IncIdEntity<Integer> {

    /**
     * 商品名
     */
    private String name;
}
