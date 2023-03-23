package org.rainbow.mybatis.entity.dos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.rainbow.mybatis.entity.bos.GoodsBo;
import org.rainbow.mybatis.entity.bos.ProgressBo;
import org.rainbow.mybatis.handler.JsonTypeHandler;
import org.rainbow.mybatis.handler.StringListTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.List;

/**
 * @author K
 */
@Data
@TableName(value = "requisition", autoResultMap = true)
public class Requisition {

  private String workPlace;

  private String workAddress;

  private String dept;

  private String remark;

  @TableField(typeHandler = StringListTypeHandler.class)
  private List<String> photos;

  private String contacts;

  private String contactNum;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date reqTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  private Date eta;

  private Integer status;

  @TableField(typeHandler = JsonTypeHandler.class)
  private List<ProgressBo> progress;

  @TableField(typeHandler = JsonTypeHandler.class)
  private List<GoodsBo> goods;

  @TableId(type = IdType.INPUT)
  private String id;

  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;

  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  @TableField(fill = FieldFill.INSERT)
  private Boolean deleteFlag;
}
