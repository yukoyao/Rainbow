J2Cache 整合进程内缓存和集中式缓存
===

J2Cache 是 OSChina 使用的二级缓存框架。包含两级缓存结构：
* L1: 进程内缓存 caffeine/ehcache
* L2: 集中式缓存 Redis/Memcached

设计初衷: 由于大量的缓存读取会导致 L2 的网络成为整个系统的瓶颈。因此 L1 的目标是降低对 L2 的读取机会。
该缓存框架主要是用于集群环境，但也可用于单机环境。用于避免应用重启导致 ehcache 缓存数据丢失。

读取顺序: L1 -> L2 -> DB

## 声明式缓存

org.rainbow.example.controller.BasicController 中使用 J2Cache 的 API 来进行缓存操作，这种 J2Cache API 使用起来会非常繁琐。
并且因为操作缓存的代码和业务代码混合在一起，所以 J2Cache API 具有侵入性。所以需要对当前代码进行改性，即通过AOP来对自定义缓存注解进行拦截操作。

设计如下:
* @Cache. 定义于 Controller 的方法上，用于缓存此方法的返回值。
* @CacheEvictor. 定义于 Controller 的方法上.用于使对应的缓存失效。
* CacheMethodInterceptor. 缓存拦截器，用于拦截加入缓存相关注解的方法。
* AbstractCacheAnnotationProcessor. 抽象缓存你注解处理器，为缓存提供一些公共方法。
* CacheAnnotationProcessor.缓存注解处理器，当 Controller 的方法上加入 Cache 注解时由此处理器进行缓存处理。
* CacheEvictorAnnotationProcessor. 失效缓存处理器.当 Controller 的方法上加入 CacheEvictor 注解时由此处理器进行缓存处理。
* @EnableJ2Cache. 开启缓存注解。一般在项目启动类上使用。

