JVM 加载机制和双亲委派机制
===

## JVM 加载机制

通过 Java 命令执行代码的大致流程如下：

![Java 代码执行过程](http://images.yuko.top/images/2022/03/01/clipboard.png)


类加载过程分为几步：(加载 -> 验证 -> 准备 -> 解析 -> 初始化 -> 使用 -> 卸载)

* 加载：在硬盘上查找并通过 IO 读入字节码文件，**使用到类时才会加载**，例如调用类的 main() 方法，new 对象等等，
在加载阶段会在内存中生成一个**代表这个类的java.lang.Class对象**，作为方法区这个类的各种数据的访问入口。
* 验证：验证字节码文件的正确性。
* 准备：给类的静态变量分配内存，并赋予默认值。
* 解析：将**符号引用**替换为直接引用，该阶段会把一些静态方法(符号引用，比如main()方法)替换为指向数据所存内存的指针或句柄等(直接引用)，这是所谓的
**静态链接**过程。**动态链接**则是在程序运行过程中将符号应用转化为直接应用。
* 初始化：对类的静态变量初始化为指定的值，执行静态代码块。

类被加载到方法区中后主要包含 **运行时常量池**、**类型信息**、**字段信息**、**方法信息**、**类加载器的引用**、**对应 class 实例的引用**等信息。
类加载器的引用：这个类到类加载实例的引用。
对应 class 实例的引用：类加载器在加载类信息方法放到方法区中后，会创建一个对应的 Class 类型的对象实例放到堆(Heap)中，作为开发人员访问方法区中类定义的
入口和切入点。

## 类加载器和双亲委派机制
上面的类加载过程主要是通过类加载器来完成的，Java 中类加载器主要分为：
* 引导类加载器：负责加载支撑 JVM 运行的位于 JRE 的 lib 目录下的核心类库，比如 rt.jar、charsets.jar 等。
* 扩展类加载器：负责加载支撑 JVM 运行的位于 JRE 的 lib 目录下的 ext 扩展目录中的 JAR 类包。
* 应用程序类加载器：负责加载 ClassPath 路径下的类包，主要就是加载你自己写的类。
* 自定义加载器：负责加载用户自定义路径下的包。


### 类加载器初始化过程：
在类加载过程中会创建 JVM 启动器实例 sun.misc.Launcher。在 Launcher 构造方法内部，其创建了两个类加载器，分别是 sun.misc.Launcher.ExtClassLoader
(扩展类加载器)和 sun.misc.Launcher.AppClassLoader(应用类加载器)。JVM 默认使用 Launcher 的 getClassLoader() 方法返回的类加载器 AppClassLoader
的实例来加载我们的应用程序。

```
// Launcher 构造方法
public Launcher() {
    
    Launcher.ExtClassLoader var1;
    
    try {
        // 构建扩展类加载器，在构造的过程中将父加载器设置为 null
        var1 = Launcher.ExtClassLoader.getClassLoader();
    } catch(IOException var10) {
        throw new InternalError("Could not create extension class loader", var10);
    }
    
    try {
        // 构建应用类加载器，在构建的过程中将其父加载器设置为 ExtClassLoader
        // Launcher 的 loader 属性值为 AppClassLoader 
        this.loader = Launcher.AppClassLoader.getAppClassLoader(var1);
    } catch(IOException var9) {
         throw new InternalError("Could not create application class loader", var9);
    }
    
    Thread.currentThread().setContextClassLoader(this.loader);
    String var2 = System.getProperty("java.security.manager");
     // 省略部分代码
    .....
}
```


### 双亲委派机制

![双亲委派机制](http://images.yuko.top/images/2022/03/01/clipboard-1.png)


含义：加载某个类时会先委托父加载器寻找目标类，找不到再委托上层父加载器加载，如果所有的父加载器在自己的加载类路径下都找不到目标类，则在自己的类加载路径下
查找并载入目标类。


应用程序加载器 AppClassLoader 加载类的双亲委派机制源码，AppClassLoader 的 loadClass 方法最终会调用其父类的 ClassLoader 的 loadClass 方法，
该方法大概的逻辑：
1. 首先，检查一下指定的名称的类是否已经被加载过，如果已经加载过了，就不需要再加载，直接返回。
2. 如果此类没有加载过，那么，再判断一下是否有父加载器；如果有父加载器，则由父加载器加载(即调用parent.loadClass(name, false)) 或者调用 bootstrap
类加载器来加载。
3. 如果父加载器及 bootstrap 类加载器没有找到指定的类，那么调动当前类加载器的 findClass 方法来完成类加载。

```
// ClassLoader 的 loadClass 方法，里面实现了双亲委派机制
protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    
    synchronized (getClassLoadingLock(name)) {
        // 检查当前类加载器是否已经加载了该类
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            long t0 = System.nanoTime();
            try {
                // 如果当前加载器父加载器不为空则委托父加载器加载该类
                if (parent != null) {
                    c = parent.loadClass(name, false);
                } else {
                    // 如果当前加载器父加载器为空则委托引导类加载器加载该类
                    c = findBootStrapClassOrNull(name);
                }
            } catch(ClassNotFoundException e) {
            
            }
            
            if (c == null) {
                long t1 = System.nanoTime();
                // 调用 URLClassLoader 的 findClass 方法在加载器的类路径里查找并加载该类
                c = findClass(name);
                
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElpsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
        }
        
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }
}
```

### 双亲委派机制解决什么问题？

* 沙箱安全机制：自己写的 java.lang.String class不会被加载，这样可以防止核心 API 库被随意篡改。
* 避免类的重复加载：当父加载器已经加载了该类的时候，就没有必要子加载器再加载一遍，保证被加载类的唯一性。


### 全盘负责委托机制

含义：指的是当一个 ClassLoader 装载一个类的时候，除非显式地使用另外一个 ClassLoader，该类所依赖及引用的类也由这个 ClassLoader 载入。

### 自定义类加载器

自定义类加载器只需要继承 java.lang.ClassLoader 类，该类有两个核心方法，一个是 loadClass(String, boolean)，实现了双亲委派机制。还有一个
方法是 findClass，默认实现是空方法，所以我们自定义类加载器主要是重写 **findClass方法**

```
public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader {

        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name  = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                // defineClass 将一个字节数组转为 Class 对象，这个字节数组是 class 文件读取后最终的字节数组
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 初始化自定义类加载器，会先初始化父类 ClassLoader，其中会把自定类加载器的父加载器设置为应用程序类加载器AppClassLoader
        MyClassLoader classLoader = new MyClassLoader("D:/Idea_project/breaker/target");
        Class clazz = classLoader.loadClass("org.rainbow.breaker.load.Test1");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("print", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
```

## Tomcat 打破双亲委派机制

Tomcat 打破双亲委托机制的原因是什么？要理解这个问题之前需要了解 Tomcat 作为一个 Web 容器，需要处理什么问题？

1. 一个 Web 容器可能需要部署两个应用程序。不同的应用程序可能会**依赖同一个第三方类库的不同版本**，不能要求同一个类库在同一台服务器上只有一份，因此
要保证每个应用程序的类库都是独立的且相互隔离。
2. 部署在同一个 web 容器中同一个类库相同的版本要求可以共享。
3. web 容器也有自己依赖的类库，不能与应用程序的类库相混淆。基于安全考虑，应该将容器的类库和程序的类库分隔开。
4. web 容器要支持 jsp 的修改，jsp 最终也是要编译成 class 文件才能在虚拟机中运行，但是很多时候需要在程序运行期间对 jsp 进行修改，web 容器需要
支持 jsp 修改后不用重启。

再次回到问题本身。为什么 Tomcat 需要打破双亲委托机制？如果使用默认的类加载机制是无法加载两个相同类库的不同版本的，因为默认的类加载器不管你是什么版本，
只在乎全限定名，并且只有一份。 还有就是 jsp 的热加载问题，jsp 文件本质上就是 class 文件，那么如果修改了，但类名还是一样，类加载器会直接到方法区中
取已经存在的，修改后的 jsp 是不会加载的，这个时候可以直接把 jsp 对应的类卸载掉，每个 jsp 对应唯一的类加载器，当一个 jsp 文件修改了，就直接卸载掉这个
jsp 类加载器。重新创建类加载器，重新加载 jsp 文件。

### Tomcat 自定义加载器详解

![Tomcat 自定义加载器](http://images.yuko.top/images/2022/03/01/clipboard-2.png)

tomcat 的几个主要类加载器：
* commonLoader：Tomcat 最基本的类加载器，加载路径中的 class 可以被 Tomcat 容器本身及各个 Webapp 访问。
* catalinaLoader：Tomcat 私有的类加载器，加载路径中的 class 对 Webapp 不可见。
* sharedLoaded：各个 Webapp 共享的类加载器，加载路径中的 class 对于所有 Webapp 可见，但是对于 tomcat 容器不可见。
* WebappClassLoader：各个 Webapp 私有的类加载器，加载路径中的 class 只对当前的 Webapp 可见，比如加载 war 包里相关的类，每个 war 包应用
都有自己的 WebappClassLoader，实现相互隔离，比如不同的 war 包应用引入不同的 spring 版本，这样实现就能加载各自的 spring 版本。

从图中的委派关系中可以看出：
CommonClassLoader 加载的类能被 CatalinaClassLoader 和 SharedClassLoader 所使用。而 CatalinaClassLoader 和 SharedClassLoader 
各自加载的类能保证与对方相互隔离。
WebAppClassLoader 可以使用 SharedClassLoader 加载到的类，但各个 WebAppClassLoader 实例之间相互隔离。
而 JasperLoader 的加载范围仅仅是这个 JSP 文件所编译出来的那一个 .class 文件，它出现的目的就是为了被丢弃；当 web 容器检测到 JSP 文件被修改时，
会替换到目前的 JasperLoader 的实例，并通过再建立一个新的 JSP 类加载器来实现 JSP 文件的热加载功能。

很明显，Tomcat 类加载机制并没有遵循双亲委派模型，**每个 webappClassLoader 加载自己目录下的 class 文件，不会传递给父类加载器，打破了双亲委派机制**

**模拟实现 Tomcat 的 webappClassLoader 加载自己 war 包应用内不同版本类实现共存与隔离**

```
public class MyClassLoaderTest2 {

    static class MyClassLoader extends ClassLoader {

        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws IOException {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        /**
         * 重写类加载逻辑 实现自己的加载逻辑 不委派给双亲加载
         */
        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                Class<?> c = findLoadedClass(name);

                if (c == null) {
                    long t1 = System.nanoTime();

                    // 非自定义的类还是走双亲委派
                    if (!name.startsWith("org.rainbow.breaker.load")) {
                        c = this.getParent().loadClass(name);
                    } else {
                        c = findClass(name);
                    }

                    sun.misc.PerfCounter.getFindClasses().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }

                if (resolve) {
                    resolveClass(c);
                }
                return c;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("D:/Idea_project/breaker/target/classes/");
        Class clazz = classLoader.loadClass("org.rainbow.breaker.load.p1.Test");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("print", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader());

        System.out.println();
        MyClassLoader classLoader1 = new MyClassLoader("D:/Idea_project/breaker/target/classes/");
        Class clazz1 = classLoader1.loadClass("org.rainbow.breaker.load.p2.Test");
        Object obj1 = clazz1.newInstance();
        Method method1= clazz1.getDeclaredMethod("print", null);
        method1.invoke(obj1, null);
        System.out.println(clazz1.getClassLoader());
    }
}


=============== 运行结果 ===============
p1. Test
org.rainbow.breaker.load.MyClassLoaderTest2$MyClassLoader@27fa135a

p2. Test
org.rainbow.breaker.load.MyClassLoaderTest2$MyClassLoader@2b71fc7e
```

> 注意：同一个 JVM 内，两个相同包名和类名的类对象可以共存，因为它们的类加载器可以不一样。所以除了看全限定名之外，还需要看类加载器是否为同一个，如果为同一个则认为它们是同一个。
