package org.rainbow.prize.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 奖品
 *
 * @author: K
 * @date: 2022/04/06 22:51
 */
@Data
@ApiModel("奖品")
@TableName("card_product")
public class CardProduct implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("名称")
  private String name;

  @ApiModelProperty("图片相对路径")
  private String pic;

  @ApiModelProperty("奖品介绍")
  private String info;

  @ApiModelProperty("价格")
  private BigDecimal price;


}
