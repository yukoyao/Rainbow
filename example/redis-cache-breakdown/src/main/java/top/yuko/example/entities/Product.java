package top.yuko.example.entities;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: K
 * @date: 2022/02/17 13:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("商品信息")
public class Product {

  private Integer id;

  private String name;

  private Integer price;

  private String detail;



}
