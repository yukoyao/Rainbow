package org.rainbow.prize.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户中奖信息表
 *
 * @author: K
 * @date: 2022/04/06 23:35
 */
@Data
@TableName("card_user_hit")
@ApiModel(value = "用户中奖信息表")
public class CardUserHit implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("活动id")
  private Integer gameId;

  @ApiModelProperty("用户id")
  private Integer userId;

  @ApiModelProperty("奖品id")
  private Integer productId;

  @ApiModelProperty("中奖时间")
  private LocalDateTime hitTime;

}
