Redisson
===

### 通用配置

| 参数名     | 参数说明            | 默认值                                 | 详细说明 |
|---------|-----------------|-------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| codec   | 编码              | org.redisson.codec.JsonJacksonCodec | Redisson 的对象编码类是用于将对象进行序列化和反序列化，以实现对该对象在 Redis 里的读取和存储                                                                                                                                                                                                                                                                 |
| threads | 线程池数量           | 当前处理核数量 * 2                         | 这个线程池数量被所有的 RTopic 对象监听器，RRemoteService 调用者和 RExecutorService 任务共同共享                                                                                                                                                                                                                                                   |
| nettyThreads   | Netty 线程池数量     | 当前处理核数量 * 2                         | 这个线程池数量是在一个 Redisson 实例内，被其创建的所有分布式数据类型和服务，以及底层客户端所一同共享的线程池里保存的线程数量                                                                                                                                                                                                                                                    |
| executor   | 线程池             | 无                                   | 单独提供一个用来执行所有 RTopic 对象监听器，RRemoteService 调用者和 RExecutorService 任务的线程池实例                                                                                                                                                                                                                                                |
| eventLoopGroup   | null               | 无                                   | 用于特别指定一个 EventLoopGroup.EventLoopGroup 是用来处理所有通过 Netty 与 Redis 服务之间的连接发送和接受的消息。一个 Redisson 都会在默认情况下自己创建管理一个 EventLoopGroup 实例。因此，如果在同一个 JVM 里面可能存在多个 Redisson 实例的情况下，采取这个配置实现多个 Redisson 实例共享一个 EventLoopGroup 的目的。只有 `io.netty.channel.epoll.EpollEventLoopGroup` 和  `io.netty.channel.nio.NioEventLoopGroup` 才是允许的类型 |
| transportMode   | 传输模式            | 默认为 `TransportMode.NIO`             | `TransportMode.NIO` 和 `TransportMode.EPOLL`                                                                                                                                                                                                                                                                            |
| lockWatchdogTimeout   | 监控锁的看门狗超时 单位：毫秒 | 30000                               | 该参数只适用于分布式锁的加锁请求中未明确使用 `leaseTimeout` 参数的情况                                                                                                                                                                                                                                                                            |
| keepPubSubOrder   | 保持订阅发布的情况       | true                                | 通过该参数来修改是否按订阅发布消息的接收顺序出来顺序，如果选否将对消息实行并行处理，该参数只适用于订阅发布消息的情况。                                                                                                                                                                                                                                                            |

### 单机模式配置

| 参数名                                   | 参数说明             | 默认值   | 详细说明                                                                                                  |
|---------------------------------------|------------------|-------|-------------------------------------------------------------------------------------------------------|
| address                               | 节点地址             | null  | `host:port`的格式来指定节点地址                                                                                 |
| subscriptionConnectionMinimumIdleSize | 发布和订阅连接的最小空闲连接数  | 1     | 用于发布和订阅连接的最小保持连接数(长连接)。Redisson 内部经常通过发布和订阅来实现许多功能，长期保持一定数量的发布订阅连接是必须的                                |
| subscriptionConnectionPoolSize        | 发布和订阅连接池大小       | 50    | 用于发布和订阅连接的连接池最大容量。连接池的连接数量自动弹性伸缩                                                                      |
| connectionMinimumIdleSize             | 最小空闲连接数          | 32    | 最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时写入反应速度                                                                 |
| connectionPoolSize                    | 连接池大小            | 64    | 在启用该功能以后，Redisson将会监测DNS的变化情况。                                                                        |
| dnsMonitoringInterval                 | DNS监测时间间隔，单位：毫秒  | 5000  | 监测 DNS 的变化情况的时间间隔                                                                                     |
| idleConnectionTimeout                 | 连接空闲超时，单位：毫秒     | 10000 | 如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉，时间单位是毫秒                               |
| connectTimeout                        | 连接超时，单位：毫秒       | 10000 | 同节点建立连接时的等待超时。时间单位是毫秒。                                                                                |
| timeout                               | 命令等待超时，单位：毫秒     | 3000  | 等待节点回复命令的时间。该时间从命令发送成功时开始计时                                                                           |
| retryAttempts                         | 命令失败重试次数         | 3     | 如果尝试达到 `retryAttempts`(命令失败重试次数)仍然不能将命令发送至某个指定的节点时，将抛出异常。如果尝试在此限制之内发送成功，则开始启用 timeout(命令等待超时) 计时      |
| retryInterval                         | 命令重试发送时间间隔，单位：毫秒 | 1500  | 在某个节点执行相同或不同命令时，连续失败 failedAttempts (执行失败最大次数)时，该节点将从可用列表里清除，直到 reconnectionTimeout(重新连接时间间隔)超时以后再次尝试 |
| database                              | 数据库编号            | 0     | 尝试连接的数据库编号                                                                                            |
| password                              | 密码               | null  | 用于节点身份验证的密码                                                                                           |
| subscriptionsPerConnection            | 单个连接最大订阅数量       | 5     | 每个连接的最大订阅数量                                                                                           |
| clientName            | 客户端名称            | null  | 在Redis节点里显示的客户端名称                                                                                     |
| sslEnableEndpointIdentification            | 启用SSL终端识别            | true  | 开启SSL终端识别能力                                                                                           |
| sslProvider            | SSL实现方式            | JDK   | 确定采用哪种方式（JDK或OPENSSL）来实现SSL连接。                                                                        |
| sslTruststore            | SSL信任证书库路径            | null     | 指定SSL信任证书库的路径                                                                                         |
| sslTruststorePassword            | SSL信任证书库密码            | null     | 指定SSL信任证书库的密码                                                                                         |
| sslKeystore            | SSL钥匙库路径            | null     | 指定SSL钥匙库的路径                                                                                           |
| sslKeystorePassword            | SSL钥匙库密码            | null     | 指定SSL钥匙库的密码                                                                                                      |

