# 🌈 RainBow 彩虹 [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

厚积薄发

#### 学习方法总结

1. 切勿囫囵吞枣，需要学习之后自己总结，定期删改之前的学习总结，巩固之前的学习。
2. 带着问题去学习思考。



#### 目录

```
Rainbow
├── doc -- 文档部分  
├── example -- 示例部分 
├── spring-lab -- spring 示例部分 
```






## 文档部分

#####  git 部分

* [Commit message 和 Change log 编写指南](https://github.com/yukoyao/Rainbow/blob/main/doc/git/Commit%20message%20%E5%92%8C%20Change%20log%20%E7%BC%96%E5%86%99%E6%8C%87%E5%8D%97.md)

##### 项目构建部分

* [使用 jgitver-maven-plugin 定义项目的 pom 版本](https://github.com/yukoyao/Rainbow/blob/main/doc/build/%E4%BD%BF%E7%94%A8%20jgitver-maven-plugin%20%E5%AE%9A%E4%B9%89%E9%A1%B9%E7%9B%AE%E7%9A%84%20pom%20%E7%89%88%E6%9C%AC.md)

##### Docker 的介绍和使用

* [Docker 简介和安装](https://github.com/yukoyao/Rainbow/blob/main/doc/docker/Docker%20%E7%AE%80%E4%BB%8B%E5%92%8C%E5%AE%89%E8%A3%85.md)
* [Docker 目录挂载和多容器间通信](https://github.com/yukoyao/Rainbow/blob/main/doc/docker/Docker%20%E7%9B%AE%E5%BD%95%E6%8C%82%E8%BD%BD%E5%92%8C%E5%A4%9A%E5%AE%B9%E5%99%A8%E9%97%B4%E9%80%9A%E4%BF%A1.md)
* [Docker 制作精简镜像](https://github.com/yukoyao/Rainbow/blob/main/doc/docker/Docker%20%E5%88%B6%E4%BD%9C%E7%B2%BE%E7%AE%80%E9%95%9C%E5%83%8F.md)
* [Docker 在 Spring Boot Java App 上实现持续集成/持续交付](https://github.com/yukoyao/Rainbow/blob/main/example/springboot-cicd/README.md) - 未开始

##### Redis

* [Redis 过期策略&内存淘汰策略](https://github.com/yukoyao/Rainbow/blob/main/doc/redis/Redis%20%E8%BF%87%E6%9C%9F%E7%AD%96%E7%95%A5%26%E5%86%85%E5%AD%98%E6%B7%98%E6%B1%B0%E7%AD%96%E7%95%A5.md)

##### JVM

* [JVM 加载机制和双亲委派机制](https://github.com/yukoyao/Rainbow/blob/main/doc/jvm/JVM%20%E5%8A%A0%E8%BD%BD%E6%9C%BA%E5%88%B6%E5%92%8C%E5%8F%8C%E4%BA%B2%E5%A7%94%E6%B4%BE%E6%9C%BA%E5%88%B6.md)
* [JVM 内存模型剖析及优化](https://github.com/yukoyao/Rainbow/blob/main/doc/jvm/JVM%20%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B%E5%89%96%E6%9E%90%E5%8F%8A%E4%BC%98%E5%8C%96.md)
* [JVM 对象创建与内存分配机制](https://github.com/yukoyao/Rainbow/blob/main/doc/jvm/JVM%20%E5%AF%B9%E8%B1%A1%E5%88%9B%E5%BB%BA%E4%B8%8E%E5%86%85%E5%AD%98%E5%88%86%E9%85%8D%E6%9C%BA%E5%88%B6.md) - 未完成
* [垃圾收集算法与垃圾收集器ParNew&CMS](https://github.com/yukoyao/Rainbow/blob/main/doc/jvm/%E5%9E%83%E5%9C%BE%E6%94%B6%E9%9B%86%E7%AE%97%E6%B3%95%E4%B8%8E%E5%9E%83%E5%9C%BE%E6%94%B6%E9%9B%86%E5%99%A8ParNew%26CMS.md)
* [垃圾收集器G1](https://github.com/yukoyao/Rainbow/blob/main/doc/jvm/%E5%9E%83%E5%9C%BE%E6%94%B6%E9%9B%86%E5%99%A8G1.md) 

#### Spring

* [Spring 源码调试环境搭建](https://github.com/yukoyao/Rainbow/blob/main/doc/spring/Spring%20%E6%BA%90%E7%A0%81%E8%B0%83%E8%AF%95%E7%8E%AF%E5%A2%83%E6%90%AD%E5%BB%BA.md)

##### 业务数据表结构设计

* [商品相关数据库表设计]() - 未完成

##### Markdown 小技巧

* [Markdown 中使用流程图](https://github.com/yukoyao/Rainbow/blob/main/doc/markdown/markdown%E4%B8%AD%E6%B7%BB%E5%8A%A0%E6%B5%81%E7%A8%8B%E5%9B%BE.md)





## 项目实战部分

* Redis
    * [使用 Redis BitMap 实现签到功能](https://github.com/yukoyao/Rainbow/blob/main/example/redis-bitmap/README.md)
    * [使用 Redis HyperLogLog 实现 UV 指标采集和展示](https://github.com/yukoyao/Rainbow/blob/main/example/redis-HyperLogLog/README.md)
    * [使用 Redis GEO 实现附近商家](https://github.com/yukoyao/Rainbow/blob/main/example/redis-geo/README.md)
    * [布隆过滤器的使用](https://github.com/yukoyao/Rainbow/blob/main/example/redis-bloomFilter/README.md)
    * [定时批量数据的上下架(解决缓存击穿问题)](https://github.com/yukoyao/Rainbow/blob/main/example/redis-cache-breakdown/README.md)
    * [使用 redisson 解决锁丢失问题和缓存续期问题](https://github.com/yukoyao/Rainbow/blob/main/example/redis-multi-master/README.md) 
    * [Mysql 和 Redis 的数据一致性问题]()

* Redisson
  * [springboot 2.x 集成 redisson(多种模式配置)](https://github.com/yukoyao/Rainbow/blob/main/example/springboot-redisson-configuration/README.md)

* J2Cache
  * [J2Cache 整合进程内缓存和集中式缓存](https://github.com/yukoyao/Rainbow/blob/main/example/J2Cache-example/README.md)

* 业务场景
  * [红包雨业务](https://github.com/yukoyao/Rainbow/blob/main/example/RedPacketRain/README.md)
  * [秒杀项目](https://github.com/yukoyao/Rainbow/blob/main/example/seconds-kill/README.md)


## spring-lab

* lab-benchmark-tomcat-jetty-undertow -  比较 Tomcat、Jetty、undertow 
* lab-dynamic-datasource -  动态数据源
* lab-kafka -  springboot 与 kafka 集成
* lab-mybatis - springboot 与 Mybatis 集成
* lab-sharding-datasource - springboot 与 shardingProxy、shardingJDBC 集成, 实现数据分片存储、读写分离
* lab-spring-data-es - springboot 与 elasticsearch 集成
* lab-spring-data-mongo - springboot 与 mongodb 集成
* lab-spring-data-redis - springboot 与 redis 集成
* spring-jwt-access-refresh-tokens - springboot 与 Jwt 实现双 tokens 机制
