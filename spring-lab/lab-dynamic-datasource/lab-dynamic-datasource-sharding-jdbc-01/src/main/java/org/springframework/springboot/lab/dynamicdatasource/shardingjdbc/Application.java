package org.springframework.springboot.lab.dynamicdatasource.shardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author K
 */
@SpringBootApplication
@MapperScan(basePackages = "org.springframework.springboot.lab.dynamicdatasource.shardingjdbc.mapper")
@EnableAspectJAutoProxy(exposeProxy = true) // 暴露代理对象 通过设置这个属性 可以用 AopContext.currentProxy() 获取到当前类的代理对象
public class Application {
}
