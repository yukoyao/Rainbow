package org.springframework.springboot.lab.elasticsearch.bo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 商品搜索条件返回 BO
 *
 * @author K
 */
@Data
@Accessors(chain = true)
@ToString
public class ProductConditionBO {

    /**
     * 商品分类数组
     */
    private List<Category> categories;

    @Data
    public static class Category {

        /**
         * 分类编号
         */
        private Integer id;
        /**
         * 分类名称
         */
        private String name;
    }
}
