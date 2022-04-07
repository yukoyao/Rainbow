package org.rainbow.prize.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 活动规则表
 *
 * @author: K
 * @date: 2022/04/06 23:02
 */
@Data
@ApiModel("活动规则表")
@TableName("card_game_rules")
public class CardGameRules implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("活动id")
  private Integer gameId;

  @ApiModelProperty("用户等级")
  private Integer userLevel;

  @ApiModelProperty("可抽奖次数（0为不限）")
  private Integer enterTimes;

  @ApiModelProperty("最大中奖次数（0为不限）")
  private Integer goalTimes;
}
