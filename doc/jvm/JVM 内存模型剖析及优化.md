JVM 内存模型剖析及优化
===

## JVM 整体结构及内存模型

![JVM整体结构及内存模型](http://images.yuko.top/images/2022/03/03/_20220303150808.png)

## JVM 内存参数设置

![JVM整体结构及内存模型](http://images.yuko.top/images/2022/03/03/clipboard3.png)

Spring Boot 程序的 JVM 参数设置格式(Tomcat 启动需要加在 bin 目录下的 catalina.sh 文件里面)：

```
java -Xms2048M -Xmx2048M -Xmn1024M -Xss512K -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -jar microserivce-eurka-server.jar
```

-Xss: 每个线程的栈大小。 
-Xms: 设置堆的初始可用大小。默认物理内存的 1/64。
-Xmx: 设置堆的最大可用大小，默认物理内存的 1/4。
-Xmn: 新生代大小。
-XX:NewRatio: 默认 2 表示新生代占年老代的 1/2，占整个堆内存的 1/3。
-XX:SurvivorRatio: 默认 8 表示一个 survivorRatio 区占用 1/8 的 Eden 内存，即 1/10 的新生代内存。
-XX:MaxMetaspaceSize: 设置元空间最大值，默认是 -1，即不限制，或者说只受本地内存的限制，
-XX:MetaspaceSize: 指定元空间触发 Full gc 的初始阀值(元空间无固定的初始大小)，以字节为单位，默认是21M左右，达到该值就会触发 full gc 进行类型卸载，
同时收集器会对该值进行调整：如果释放了大量的空间，就适当降低该值；如果释放了很少的空间，那么在不超过 -XX:MaxMetaspaceSize(如果设置了的话)的情况下，
适当提高该值，这个跟早期的 jdk 版本的 -XX:PermSize 参数含义一样。(-XX:PermSize代表了永久代的初始容量。)

由于调整元空间大小需要 Full GC，这是非常昂贵的操作，如果应用在启动的时候发生大量的 Full GC，通常都是由于永久代或者是元空间发生了大小调整。一般这种情况，
都需要将 JVM 参数中的 MetaspaceSize 和 MaxMetaspaceSize 设置成一样的值，并设置得比初始值要大，对于 8G 物理内存的机器来说，一般设置为 256M.

而 Xss 设置小的目的是使一个线程栈里能分配的栈帧变少，而相对应的 JVM 整体的开启的线程数则会越多。



