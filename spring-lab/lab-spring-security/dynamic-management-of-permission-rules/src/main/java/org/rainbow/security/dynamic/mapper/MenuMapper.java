package org.rainbow.security.dynamic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.rainbow.security.dynamic.entity.Menu;
import java.util.List;

/**
 * @author K
 */
@Mapper
public interface MenuMapper {

  List<Menu> getAllMenu();

}
