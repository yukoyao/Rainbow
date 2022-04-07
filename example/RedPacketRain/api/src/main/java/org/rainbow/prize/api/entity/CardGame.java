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
 * 活动
 *
 * @author: K
 * @date: 2022/04/06 20:33
 */
@Data
@ApiModel("活动")
@TableName("card_game")
public class CardGame implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("活动标题")
  private String title;

  @ApiModelProperty("宣传图")
  private String pic;

  @ApiModelProperty("活动介绍")
  private String info;

  @ApiModelProperty("开始时间")
  private LocalDateTime startTime;

  @ApiModelProperty("结束时间")
  private LocalDateTime endTime;

  @ApiModelProperty("类型 (1=概率类 2=随机类)")
  private Integer type;

  @ApiModelProperty("状态 (0=新建 1=已加载)")
  private Integer status;


}
