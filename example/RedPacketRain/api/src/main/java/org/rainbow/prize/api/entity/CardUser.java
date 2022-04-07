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
 * 用户表
 *
 * @author: K
 * @date: 2022/04/06 23:06
 */
@Data
@ApiModel("用户表")
@TableName("card_user")
public class CardUser implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(type = IdType.AUTO)
  @ApiModelProperty("id")
  private Integer id;

  @ApiModelProperty("用户名")
  private String uname;

  @ApiModelProperty("头像地址")
  private String pic;

  @ApiModelProperty("密码")
  private String passwd;

  @ApiModelProperty("真实姓名")
  private String realName;

  @ApiModelProperty("身份证号码")
  private String idcard;

  @ApiModelProperty("手机号码")
  private String phone;

  @ApiModelProperty("用户级别")
  private Integer level;

  @ApiModelProperty("创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty("修改时间")
  private LocalDateTime updateTime;
}
