package org.rainbow.mybatis.entity.bos;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author K
 */
@Data
@Accessors(chain = true)
public class GoodsBo {

    /**
     * 序号
     */
    private Integer seqNo;

    /**
     * 商品编码
     */
    private String sn;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * SKU id
     */
    private Long skuId;

    /**
     * 商品说明
     */
    private String goodsExplain;

    /**
     * 采购数量
     */
    private Integer num;

}
