package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.constant;

/**
 * 数据库枚举类
 *
 * @author K
 */
public class DBConstants {

    /**
     * 事务管理器 - 订单库
     */
    public static final String TX_MANAGER_ORDERS = "ordersTransactionManager";
    /**
     * 事务管理器 - 用户库
     */
    public static final String TX_MANAGER_USERS = "usersTransactionManager";

    /**
     * JdbcTemplate - 订单库
     */
    public static final String JDBC_TEMPLATE_ORDERS = "ordersJdbcTemplate";
    /**
     * JdbcTemplate - 用户库
     */
    public static final String JDBC_TEMPLATE_USERS = "usersJdbcTemplate";
}
