Docker 制作精简镜像
===

## 镜像层(Layers)
在开始制作镜像之前，首先需要了解下镜像的原理，而这其中最重要的概念就是`镜像层(Layers)`。镜像层依赖于一系列的底层技术，
比如文件系统(filesystems)、写时复制(copy-on-write)、联合挂载(union mounts)等。

![docker-image-layers](http://yuko.top:9099/images/2022/02/22/202111121641186114969.png)

总的来说，需要记住的是：
```
在 Dockerfile 中，每一条指令都会创建一个镜像层，继而会增加整体镜像的大小。
```

举例来说：
```Dockerfile
FROM busybox
RUN mkdir /tmp/foo
RUN dd if=/dev/zero of=/tmp/foo/bar bs=1048576 count=100
RUN rm /tmp/foo/bar
```

以上 Dockerfile 干了几件事：
1. 基于一个官方的基础镜像(只有 1M 多)
2. 创建一个文件夹(/tmp/foo)和一个文件(bar)，该文件分配了 100M 大小
3. 再把这个大文件删除

实际上它什么事情都没有做，在构建成镜像之后比对前后两个镜像大小：

```
docker build -t busybox:test .
```
```bash
$ docker images | grep busybox
busybox    test     896c63dbdb96    2 seconds ago    106 MB
busybox    latest   2b8fd9751c4c    9 weeks ago      1.093 MB
```

尽管最终什么事情都没有干，却生成了一个 100 多M的镜像。原因是`在 Dockerfile 中每一条指令都会可能增加整体镜像的大小，即使它最终什么事都没做`

## 制作步骤
了解了镜像层知识，有助于制作精简的镜像，下面以开源缓存软件 `redis` 为例，一步步试验看如何制作更精简的镜像。

### 初始化构建 Redis 镜像

```dockerfile
FROM ubuntu:trusty
ENV VER 3.0.0
ENV TARBALL http://download.redis.io/releases/redis-$VER.tar.gz
# ==> Install curl and helper tools...
RUN apt-get update
RUN apt-get install -y curl make gcc
# ==> Download, compile, and install...
RUN curl -L $TARBALL | tar zxv
WORKDIR redis-$VER
RUN make
RUN make install
#...
# ==> Clean up...
WORKDIR /
RUN apt-get remove -y --auto-remove curl make gcc
RUN apt-get clean 
RUN rm -rf /var/lib/apt/lists/* /redis-$VER
#...
CMD ["redis-server"]
```

* FROM: 必须是第一行，指定一个基础镜像，此处基于 `ubuntu:trusty`
* ENV: 设置环境变量，这里设置了 `VER` 和 `TARBALL` 两个环境变量
* RUN: 最常用的 Dockerfile 指令，用于运行各种命令
* WORKDIR: 指定工作目录，相当于指令 `cd`
* CMD: 指定镜像默认执行的命令。此处默认执行执行 redis-server 命令来启动 redis。

**执行构建：**

```bash
$ docker build -t redis:lab-1 .
```

**查看大小：**

| Lab |  iamge  | Base       | Lang    | .red[*] |  Size (MB) | &nbsp;&nbsp; Memo            |
|:---:|:--------|:-----------|:-----:|:---:|---------------:|:--------------------------------|
|  1 |  redis  |  `ubuntu`  |   C   | dyn |   347.3        | &nbsp;&nbsp; base ubuntu        |

接下来开始一步步优化，使镜像的体积变小

### lab-2: 优化基础镜像

**精简1：选用更小的基础镜像**

常用的 Linux 系统镜像一般有 `ubuntu`、`centos`、`debian`，其中 `debian` 更为轻量且够用。

```
REPOSITORY          TAG        IMAGE ID         VIRTUAL SIZE
---------------     ------     ------------     ------------
centos              7          214a4932132a     215.7 MB
centos              6          f6808a3e4d9e     202.6 MB
ubuntu              trusty     d0955f21bf24     188.3 MB
ubuntu              precise    9c5e4be642b7     131.9 MB
debian              jessie     65688f7c61c4     122.8 MB
debian              wheezy     1265e16d0c28     84.96 MB
```
替换 `debian:jessie` 作为基础镜像。

**优化 Dockerfile：**
```dockerfile
FROM debian:jessie

#...
```

**执行构建：**
```bash
$ docker build -t redis:lab-2 .
```

**查看大小：**

| Lab |  image  | Base       | Lang    | .red[*] |  Size (MB) | &nbsp;&nbsp; Memo               |
|:---:|:--------|:-----------|:-----:|:---:|---------------:|:--------------------------------|
|  01 |  redis  |  `ubuntu`  |   C   | dyn |   347.3        | &nbsp;&nbsp; base ubuntu        |
|  02 |  redis  |  `debian`  |   C   | dyn |   305.7        | &nbsp;&nbsp; base debian        |

减少了 41 M。

### lab-3：串联 Dockerfile 指令

**精简2： 串联你的 Dockerfile 指令（一般是 `RUN` 指令）。**

Dockerfile 中的 RUN 指令通过 `&&` 和 `/` 支持将命令串联在一起。

**优化 Dockerfile：**
```dockerfile
FROM debian:jessie

ENV VER 3.0.0
ENV TARBALL http://download.redis.io/releases/redis-$VER.tar.gz

RUN echo "==> Install curl and helper tools..." && \
    apt-get update                              && \
    apt-get install -y curl make gcc            && \
    \
    echo "==> Download, compile, and install..."&& \
    curl -L $TARBALL | tar zxv                  && \
    cd redis-$VER                               && \
    mkae                                        && \
    make install                                && \
    ...
    echo "==> Clean up..."                      && \
    apt-get remove -y --auto-remove curl make gcc && \
    apt-get clean                               && \
    rm -rf /var/lib/apt/lists/* /redis-$VER
#...
CMD ["redis-server"]
```

**执行构建：**
```bash
$ docker build -t redis:lab-3 .
```

**查看大小：**

| Lab |  Image  | Base       | Lang    | .red[*] |  Size (MB) | &nbsp;&nbsp; Memo               |
|:---:|:--------|:-----------|:-----:|:---:|---------------:|:--------------------------------|
|  01 |  redis  |  `ubuntu`  |   C   | dyn |   347.3        | &nbsp;&nbsp; base ubuntu        |
|  02 |  redis  |  `debian`  |   C   | dyn |   305.7        | &nbsp;&nbsp; base debian        |
|  03 |  redis  |  `debian`  |   C   | dyn |   151.4        | &nbsp;&nbsp; cmd chaining       |

一下子减少 50%。

### lab-4：压缩你的镜像

**精简3：试着用命令或工具压缩你的镜像。**

docker 自带的一些命令能够协助压缩镜像，比如 `export` 和 `import`
```bash
$ docker run -d redis:lab-3
$ docker export 71b1c0ad0a2b | docker import - redis:lab-4
```

但麻烦的是需要先将容器运行起来，而且这个过程中你会丢失镜像原有的一些信息，比如：导出端口，环境变量，默认指令。

所以一般通过命令行来精简镜像都是实验性的，那么这里再推荐一个小工具： [docker-squash](https://github.com/jwilder/docker-squash)。用起来更简单方便，并且不会丢失原有镜像的自带信息。

**压缩操作：**
```bash
$ docker save redis:lab-3 \
  | sudo docker-squash -verbose -t redis:lab-4 \
  | docker load 
```

**对比大小：**

| Lab |  Image  | Base       | PL    | .red[*] |  Size (MB) | &nbsp;&nbsp; Memo               |
|:---:|:--------|:-----------|:-----:|:---:|---------------:|:--------------------------------|
|  01 |  redis  |  `ubuntu`  |   C   | dyn |   347.3        | &nbsp;&nbsp; base ubuntu        |
|  02 |  redis  |  `debian`  |   C   | dyn |   305.7        | &nbsp;&nbsp; base debian        |
|  03 |  redis  |  `debian`  |   C   | dyn |   151.4        | &nbsp;&nbsp; cmd chaining       |
|  04 |  redis  |  `debian`  |   C   | dyn |   151.4        | &nbsp;&nbsp; docker-squash      |

有些情况下，压缩并不那么明显。

### lab-5：使用最精简的 base image

使用 `scratch` 或者 `busybox` 作为基础镜像。

关于 `scratch`:

* 一个空镜像，只能用于构建镜像，通过 `FROM scratch`
* 在构建一些基础镜像，比如 `debian`、`busybox` 非常有用
* 用于构建超少镜像，比如构建一个含有所有库的二进制文件

关于 `busybox`:

* 只有 1 ~ 5 M 的大小
* 包含了常用的 UNIX 工具
* 非常方便构建小镜像

这些超小的基础镜像，结合能生成静态原生的 ELF 文件的编译语言，比如 C/C++、GO，特别方便构建超小的镜像。

### lab-6：提取动态链接的 .so 文件

```bash
$ cat /etc/os-release

NAME="Ubuntu"
VERSION="14.04.2 LTS, Trusty Tahr"
```

```bash
$ uname -a
Linux localhost 3.13.0-46-generic #77-Ubuntu SMP
Mon Mar 2 18:23:39 UTC 2015
x86_64 x86_64 x86_64 GNU/Linux
```

ldd：打印共享的依赖库
```bash
$ ldd  redis-3.0.0/src/redis-server
    linux-vdso.so.1 =>  (0x00007fffde365000)
    libm.so.6 => /lib/x86_64-linux-gnu/libm.so.6 (0x00007f307d5aa000)
    libpthread.so.0 => /lib/x86_64-linux-gnu/libpthread.so.0 (0x00007f307d38c000)
    libc.so.6 => /lib/x86_64-linux-gnu/libc.so.6 (0x00007f307cfc6000)
    /lib64/ld-linux-x86-64.so.2 (0x00007f307d8b9000)
```

将所有需要的 .so 文件打包：
```bash
$ tar ztvf rootfs.tar.gz
4485167  2015-04-21 22:54  usr/local/bin/redis-server
1071552  2015-02-25 16:56  lib/x86_64-linux-gnu/libm.so.6
 141574  2015-02-25 16:56  lib/x86_64-linux-gnu/libpthread.so.0
1840928  2015-02-25 16:56  lib/x86_64-linux-gnu/libc.so.6
 149120  2015-02-25 16:56  lib64/ld-linux-x86-64.so.2
```

再制作成 Dockerfile:
```dockerfile
FROM scratch
ADD rootfs.tar.gz /
COPY redis.conf /etc/redis/redis.conf
EXPOSE 6379
CMD ["redis-server"]
```

执行构建：
```bash
$ docker build  -t redis-05  .
```

查看大小：

| Lab |         | Base       | PL    | .red[*] |  Size (MB) | &nbsp;&nbsp; Memo               |
|:---:|:--------|:-----------|:-----:|:---:|---------------:|:--------------------------------|
|  01 |  redis  |  `ubuntu`  |   C   | dyn |   347.3        | &nbsp;&nbsp; base ubuntu        |
|  02 |  redis  |  `debian`  |   C   | dyn |   305.7        | &nbsp;&nbsp; base debian        |
|  03 |  redis  |  `debian`  |   C   | dyn |   151.4        | &nbsp;&nbsp; cmd chaining       |
|  04 |  redis  |  `debian`  |   C   | dyn |   151.4        | &nbsp;&nbsp; docker-squash      |
|  05 |  redis  |  `scratch` |   C   | dyn |    7.73        | &nbsp;&nbsp; rootfs: .so        |

显著压缩了镜像。

测试一下：

```bash
$ docker run -d --name redis-05 redis-05

$ redis-cli  -h  \
  $(docker inspect -f '{{.NetworkSettings.IPAddress}}' redis-05)

$ redis-benchmark  -h  \
  $(docker inspect -f '{{.NetworkSettings.IPAddress}}' redis-05)
```

总结一下：

1. 用 `ldd` 查出所需的 .so 文件
2. 将所有依赖压缩成 `rootfs.tar` 或 `rootfs.tar.gz`，之后打进 `scratch` 基础镜像。


### lab-7: 为 Go 应用构建精简镜像

Go 语言天生就方便用来构建精简镜像，得益于它能方便的打包成包含静态链接的二进制文件。

## 总结
1. 优化基础镜像
2. 串接 Dockerfile 命令
3. 压缩 Docker images
4. 优化程序依赖
5. 选用更合适的开发语言
