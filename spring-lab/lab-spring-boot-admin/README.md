
### 使用 Spring Boot Admin 监控工具

Spring Boot Admin 用于管理和监控 Springboot 应用的。

被监控和管理的应用程序, 有两种方式注册到 Spring Boot Admin Server 上。

* 方式一: 被监控和管理的应用程序, 使用 Spring Boot Admin Client 库, 通过 HTTP 调用注册到 Spring Boot Admin Server 上。
* 方式二: 被监控和管理的应用程序, 注册到 Spring Cloud 继承的注册中心, 然后 Spring Boot Admin Server 通过注册中心获取到被监控和管理的应用程序。


