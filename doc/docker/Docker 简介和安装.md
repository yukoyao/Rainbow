
#### Docker 是什么？

Docker 是一个应用打包、分发、部署的工具

#### 重要概念：镜像、容器

镜像：可以理解为软件安装包，可以方便地进行传播和安装。
容器：软件安装后的状态，每个软件运行环境都是独立的、隔离的，称之为容器。

#### Docker 可以用来作什么？

* 应用分发、部署，方便传播给别人安装。特别是开源软件和提供私有部署的项目。
* 快速安装中间件。比如 Mysql / Redis / MongoDB / ELK。  
* 多个软件版本共存。比如 Redis4.0 / Redis5.0。  

#### Docker 安装

桌面版：https://www.docker.com/products/docker-desktop  
服务器版：https://docs.docker.com/engine/install/#server  

#### 更换镜像加速源

|  镜像加速器   | 镜像加速器地址  |
|  ----  | ----  |
| Docker 中国官方镜像  | https://registry.docker-cn.com |
| DaoCloud 镜像站  | http://f1361db2.m.daocloud.io |
| Azure 中国镜像 | https://dockerhub.azk8s.cn |
| 科大镜像站  | https://docker.mirrors.ustc.edu.cn |
| 阿里云  | https://<your_code>.mirror.aliyuncs.com |
| 七牛云  | https://reg-mirror.qiniu.com |
| 网易云  | https://hub-mirror.c.163.com |
| 腾讯云  | https://mirror.ccs.tencentyun.com |

```
## 1. 修改或创建 daemon.json 文件：vi /etc/docker/daemon.json
{
"registry-mirrors": ["https://docker.mirrors.ustc.edu.cn"]
}
## 2. 重启 docker 
systemctl restart docker
```

#### 简单命令

```
docker ps                                             查看当前运行中的容器
docker images                                         查看镜像列表
docker rm <container-id> / <container-name>           删除指定 id / name 的容器
docker start/stop <container-id> / <container-name>   停止/启动指定 id / name 的容器
docker rmi <container-id>                             删除指定 id 的镜像
docker volumn ls                                      查看 volumn 列表
docker network ls                                     查看网络列表
```
