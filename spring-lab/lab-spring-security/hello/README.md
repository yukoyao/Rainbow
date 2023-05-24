### 入门示例

#### 步骤

1. `pom.xml` 中引入 `spring-boot-starter-security`。
2. 无需任何配置，启动项目。就能看到默认的登录页面。
3. 接下来使用用户名 user 和控制台生成的密码就能登录成功。

只要引入 `Spring Security` 就能开箱即用。其中 springBoot 在背后完成很多工作:
1. 开启 `Spring Security` 的自动化配置。开启后，会自动创建一个名为 `springSecurityChain` 的过滤器，
并注入到过 Spring 容器中。这个过滤器将负责所有的安全管理。包括：认证、授权、重定向到登录页面，(`springSecurityChain`
实际上代理了 Spring Security 的过滤器链)
2. 创建一个 UserDetailsService 实例，UserDetailsService 负责提供用户数据。默认的用户数据基于内存的用户，
用户名为 `user`, 密码则是随机生成的 UUID 字符串。
3. 开启 CSRF 攻击防御。
4. 开启会话固定攻击防御。
5. 集成 X-XSS-Protection。
6. 集成 X-Frame-Options 以防止单击劫持。

