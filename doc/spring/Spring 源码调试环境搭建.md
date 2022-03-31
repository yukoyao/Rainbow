Spring 源码调试环境搭建
===

> 基于 Spring v5.2.19.RELEASE + GradleWrapper + Jdk 1.8


### Spring 版本含义

* alpha: 内测版，bug 多，还在迭代功能。
* beta: 公测版，比 alpha 稳定一些，还在迭代功能。
* RC: 候选版，经过多个 beta 版本后逐渐稳定，基本上不会添加新功能了，修复完 bug 即可进入正式发布版。
* GA、RELEASE、Stable、Final: 正式版，bug 少，适合生产使用。

额外，M1，M2版本是里程碑的标记，代表着有重大改进的版本。

### 编译工作

1. 使用 git 下载对应的稳定版本的 Spring 源码。

```
git clone -b v5.2.19.RELEASE https://github.com/spring-projects/spring-framework.git
```

2. 等待 IDEA 自动构建项目。

3. 添加测试模块代码
    * 创建新的 Module. File ->  New -> Module -> gradle
    * 在新建的 Module 中的 build.gradle 添加依赖。
    ```
    dependencies {
        testCompile group: 'junit', name: 'junit', version: '4.12' 
        compile(project(":spring-context"))     
    }
    ```
   * 添加 Bean。
   ```java
    @Service
    public class UserServiceImpl {
    
        public void sayHi() {
            System.out.println("Hello Spring!");
        }    
    }
   ```
   * 添加启动配置类
   ```java
    @Configuration
    @ComponentScan("org.springframework.beans")
    public class MainStat {
    
        public static void main(String[] args) {
            ApplicationContext context = new AnnotationConfigApplicationContext(MainStat.class);
            UserServiceImpl bean = context.getBean(UserServiceImpl.class);
            bean.sayHi();
        }    
    }
   ```
