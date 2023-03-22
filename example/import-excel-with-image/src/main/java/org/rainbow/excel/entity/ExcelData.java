package org.rainbow.excel.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author K
 */
@Data
public class ExcelData {

  private String name;

  private String unit;

  private String brand;

  private String model;

  private String sn;

  private String info;

  private BigDecimal price;

  private Integer yearPurchase;

  private String remark;

  private List<String> picUrls;

  private String category1;

  private String category2;

  private String category3;

  private String deleteFlag;

  private String type;
}
