package org.rainbow.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author K
 */
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        // 将 List 转换成逗号分隔的字符串，并将其设置为 PreparedStatement 的参数
        ps.setString(i, String.join(",", parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        // 从 ResultSet 中获取字段值，并将其转换成 List
        String columnValue = rs.getString(columnName);
        return convertToList(columnValue);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 从 ResultSet 中获取字段值，并将其转换成 List
        String columnValue = rs.getString(columnIndex);
        return convertToList(columnValue);
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 从 CallableStatement 中获取字段值，并将其转换成 List
        String columnValue = cs.getString(columnIndex);
        return convertToList(columnValue);
    }

    private List<String> convertToList(String columnValue) {
        if (columnValue == null || columnValue.isEmpty()) {
            return new ArrayList<>();
        } else {
            return Arrays.asList(columnValue.split(","));
        }
    }
}
