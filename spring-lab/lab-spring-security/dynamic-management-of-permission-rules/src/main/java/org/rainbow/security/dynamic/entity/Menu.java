package org.rainbow.security.dynamic.entity;

import lombok.Data;
import java.util.List;

/**
 * @author K
 */
@Data
public class Menu {

  private Integer id;

  private String pattern;

  private List<Role> roles;

}
