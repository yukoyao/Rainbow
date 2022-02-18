### 目录挂载

#### 目录挂载用于处理什么问题？

* 使用 Docker 启动后，改了项目代码文件不会立刻生效，需要重新 build 和 run。
* 容器产生的数据，比如 log 文件、数据库备份文件，容器删除之后就丢失了。

#### 几种挂载方式？

* `bind mount` 直接把宿主机目录映射到容器内，适合挂项目代码和配置文件。可挂多个容器上。
* `volumn` 由容器创建和管理，创建在宿主机，所以删除容器不会丢失，官方推荐，更高效。Linux 文件系统，适合存储数据库数据，可挂在容器上。
* `tmpfs mount` 适合存储临时文件，存宿主内存中，不可多容器共享。


```
`bind mount` 方式使用绝对路径 `-v /data/code:/app`
`volumn` 方式只需要一个名字 `-v db-data:/app`
```


### 多容器通信

项目间往往不是独立运行的，需要多个中间件。比如数据库、缓存这些配合运作。

#### 创建虚拟网络

要想多容器之间通信，只需要把它们放在同一个网络即可。

#### 示例

##### 创建一个名为 `test-net` 的网络

```
docker network create test-net
```

##### 运行 Redis 在 `test-net` 终端里面，网络别名 redis

```
docker run -d -name redis --network test-net --network-alias redis redis:latest
```

##### 修改 Web 代码中 redis 连接地址

```
const redis = require('redis');
let rds = redis.createClient({url: "redis://redis:6379"});
rds.on('connect', ()=> console.log('redis connect ok'));
rds.connect();
```

##### 运行 web 项目，与 Redis 处于同一个网络

```
docker run -p 8080:8080 --name test -v /data/code:/app --network test-net -d test:v1
```


