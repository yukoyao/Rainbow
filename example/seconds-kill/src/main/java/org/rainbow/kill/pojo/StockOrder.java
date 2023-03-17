package org.rainbow.kill.pojo;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * @author K
 */
@Getter
@Setter
public class StockOrder {

  private Integer id;

  private Integer sid;

  private String name;

  private Date createTime;
}
