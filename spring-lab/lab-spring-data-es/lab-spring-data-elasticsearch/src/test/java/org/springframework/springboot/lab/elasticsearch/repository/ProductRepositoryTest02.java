package org.springframework.springboot.lab.elasticsearch.repository;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.springboot.lab.elasticsearch.Application;
import org.springframework.springboot.lab.elasticsearch.bo.ProductConditionBO;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductRepositoryTest02 {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test() {
        // 创建 ES 搜索条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withIndices("product");
        // 筛选
        nativeSearchQueryBuilder.withQuery(QueryBuilders.multiMatchQuery("可爱",
                "name", "sellPoint", "categoryName"));
        // 聚合
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("cids").field("cid"));
        // 执行查询
        ProductConditionBO res = elasticsearchTemplate.query(nativeSearchQueryBuilder.build(), response -> {
            ProductConditionBO result = new ProductConditionBO();
            // categoryIds 聚合
            Aggregation categoryIdsAggregation = response.getAggregations().get("ids");
            if (categoryIdsAggregation != null) {
                result.setCategories(new ArrayList<>());
                for (LongTerms.Bucket bucket : (((LongTerms) categoryIdsAggregation).getBuckets())) {
                    result.getCategories().add(new ProductConditionBO.Category().setId(bucket.getKeyAsNumber().intValue()));
                }
            }
            // 返回结果
            return result;
        });
        System.out.println(res);
    }
}
