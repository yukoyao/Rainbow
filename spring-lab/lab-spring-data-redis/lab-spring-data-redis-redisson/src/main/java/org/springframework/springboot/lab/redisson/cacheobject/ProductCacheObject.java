package org.springframework.springboot.lab.redisson.cacheobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品缓存对象
 *
 * @author K
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCacheObject {

    /**
     * 产品编号
     */
    private Integer id;

    /**
     * 产品名
     */
    private String name;

    /**
     * 产品分类编号
     */
    private Integer cid;

    @Override
    public String toString() {
        return "ProductCacheObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cid=" + cid +
                '}';
    }
}
