package org.rainbow.security.dynamic.service;

import org.rainbow.security.dynamic.entity.Menu;
import org.rainbow.security.dynamic.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author K
 */
@Service
public class MenuService {

  @Autowired
  private MenuMapper menuMapper;

  public List<Menu> getAllMenu() {
    return menuMapper.getAllMenu();
  }

}
