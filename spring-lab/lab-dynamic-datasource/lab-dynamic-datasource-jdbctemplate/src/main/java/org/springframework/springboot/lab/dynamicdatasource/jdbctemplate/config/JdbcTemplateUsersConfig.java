package org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.springboot.lab.dynamicdatasource.jdbctemplate.constant.DBConstants;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author K
 */
@Configuration
public class JdbcTemplateUsersConfig {

    /**
     * 创建 users 数据源
     */
    @Bean(name = "usersDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.users")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建 user jdbcTemplate
     */
    @Bean(name = DBConstants.JDBC_TEMPLATE_USERS)
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource());
    }

    /**
     * 创建 users 数据源的 TransactionManager 事务管理器
     */
    @Bean(name = DBConstants.TX_MANAGER_USERS)
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }
}
