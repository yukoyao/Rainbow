# 🌈 RainBow 彩虹 [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

厚积薄发

#### 目录

```
Rainbow
├── doc -- 文档部分  
├── example -- 示例部分 
```

#### 过程

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

##### 业务数据表结构设计

* [商品相关数据库表设计]() - 未完成

##### 项目实战部分

* Redis
    * [使用 Redis BitMap 实现签到功能](https://github.com/yukoyao/Rainbow/blob/main/example/redis-bitmap/README.md)
    * [使用 Redis HyperLogLog 实现 UV 指标采集和展示](https://github.com/yukoyao/Rainbow/blob/main/example/redis-HyperLogLog/README.md)
    * [使用 Redis GEO 实现附近商家](https://github.com/yukoyao/Rainbow/blob/main/example/redis-geo/README.md)
    * [布隆过滤器的使用](https://github.com/yukoyao/Rainbow/blob/main/example/redis-bloomFilter/README.md)
    * [定时批量数据的上下架(解决缓存击穿问题)](https://github.com/yukoyao/Rainbow/blob/main/example/redis-cache-breakdown/README.md)
    * [分布式锁解决抢购问题](https://github.com/yukoyao/Rainbow/blob/main/example/redis-distributedLock/README.md)
    * [使用 redisson 解决锁丢失问题和缓存续期问题](https://github.com/yukoyao/Rainbow/blob/main/example/redis-multi-master/README.md) 

* Redisson
  * [springboot 2.x 集成 redisson(多种模式配置)](https://github.com/yukoyao/Rainbow/blob/main/example/springboot-redisson-configuration/README.md)

