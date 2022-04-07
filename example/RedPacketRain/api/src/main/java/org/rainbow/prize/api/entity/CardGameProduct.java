package org.rainbow.prize.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动奖品关联表
 *
 * @author: K
 * @date: 2022/04/06 22:57
 */
@Data
@ApiModel("活动奖品关联表")
@TableName("card_game_product")
public class CardGameProduct {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty("id")
  @TableId(type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("活动id")
  private Integer gameId;

  @ApiModelProperty("奖品id")
  private Integer productId;

  @ApiModelProperty("总价值")
  private Integer amount;

}
