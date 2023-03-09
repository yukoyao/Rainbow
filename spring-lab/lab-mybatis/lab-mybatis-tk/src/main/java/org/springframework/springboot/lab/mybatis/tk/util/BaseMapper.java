package org.springframework.springboot.lab.mybatis.tk.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author K
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
