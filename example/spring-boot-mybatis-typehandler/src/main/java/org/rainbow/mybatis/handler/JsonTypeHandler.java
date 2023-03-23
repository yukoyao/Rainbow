package org.rainbow.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author K
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<List<T>> {

  private final Class<T> clazz;

  public JsonTypeHandler(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType)
      throws SQLException {
    String json = JSON.toJSONString(parameter);
    ps.setString(i, json);
  }

  @Override
  public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String json = rs.getString(columnName);
    if (json != null) {
      try {
        // 尝试解析为 JSON 数组
        return JSON.parseArray(json, clazz);
      } catch (JSONException e) {
        // 如果解析失败，尝试解析为单个 JSON 对象
        T obj = JSON.parseObject(json, clazz);
        return Collections.singletonList(obj);
      }
    }
    return null;
  }

  @Override
  public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String json = rs.getString(columnIndex);
    if (json != null) {
      try {
        // 尝试解析为 JSON 数组
        return JSON.parseArray(json, clazz);
      } catch (JSONException e) {
        // 如果解析失败，尝试解析为单个 JSON 对象
        T obj = JSON.parseObject(json, clazz);
        return Collections.singletonList(obj);
      }
    }
    return null;
  }

  @Override
  public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String json = cs.getString(columnIndex);
    if (json != null) {
      try {
        // 尝试解析为 JSON 数组
        return JSON.parseArray(json, clazz);
      } catch (JSONException e) {
        // 如果解析失败，尝试解析为单个 JSON 对象
        T obj = JSON.parseObject(json, clazz);
        return Collections.singletonList(obj);
      }
    }
    return null;
  }
}
