# 🌈 RainBow 彩虹 [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

厚积薄发

#### 目录

```
Rainbow
├── doc -- 文档部分  
├── example -- 示例部分 
```

#### 过程

> git 部分

* [Commit message 和 Change log 编写指南](https://github.com/Yaoyukang1017/Rainbow/blob/main/doc/git/Commit%20message%20%E5%92%8C%20Change%20log%20%E7%BC%96%E5%86%99%E6%8C%87%E5%8D%97.md)

> 项目构建部分

* [使用 jgitver-maven-plugin 定义项目的 pom 版本](https://github.com/Yaoyukang1017/Rainbow/blob/main/doc/build/%E4%BD%BF%E7%94%A8%20jgitver-maven-plugin%20%E5%AE%9A%E4%B9%89%E9%A1%B9%E7%9B%AE%E7%9A%84%20pom%20%E7%89%88%E6%9C%AC.md)

> Docker 的介绍和使用

* [Docker 简介和安装](https://github.com/Yaoyukang1017/Rainbow/blob/main/doc/docker/Docker%20%E7%AE%80%E4%BB%8B%E5%92%8C%E5%AE%89%E8%A3%85.md)
* [Docker 目录挂载和多容器间通信](https://github.com/Yaoyukang1017/Rainbow/blob/main/doc/docker/Docker%20%E7%9B%AE%E5%BD%95%E6%8C%82%E8%BD%BD%E5%92%8C%E5%A4%9A%E5%AE%B9%E5%99%A8%E9%97%B4%E9%80%9A%E4%BF%A1.md)
* [Docker 在 Spring Boot Java App 上实现持续集成/持续交付](https://github.com/Yaoyukang1017/Rainbow/blob/main/example/springboot-cicd/README.md)

> 项目实战部分

* Redis
    * [使用 Redis BitMap 实现签到功能](https://github.com/Yaoyukang1017/Rainbow/blob/main/example/redis-bitmap/README.md)
    * [使用 Redis HyperLogLog 实现 UV 指标采集和展示](https://github.com/Yaoyukang1017/Rainbow/blob/main/example/redis-HyperLogLog/README.md)
    * [使用 Redis GEO 实现附近商家](https://github.com/Yaoyukang1017/Rainbow/blob/main/example/redis-geo/README.md)
    * [布隆过滤器的使用](https://github.com/Yaoyukang1017/Rainbow/blob/main/example/redis-bloomFilter/README.md)
    * [定时批量数据的上下架(解决缓存击穿问题)](https://github.com/Yaoyukang1017/Rainbow/blob/main/example/redis-cache-breakdown/README.md)
