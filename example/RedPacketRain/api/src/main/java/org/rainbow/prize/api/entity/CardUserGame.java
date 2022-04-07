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
 * 用户活动关联表
 *
 * @author: K
 * @date: 2022/04/06 23:31
 */
@Data
@TableName("card_user_game")
@ApiModel("用户活动关联表")
public class CardUserGame implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("用户id")
  private Integer userId;

  @ApiModelProperty("活动id")
  private Integer gameId;

  @ApiModelProperty("参与时间")
  private LocalDateTime createtime;
}
