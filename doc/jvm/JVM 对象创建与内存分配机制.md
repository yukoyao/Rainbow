JVM 对象创建与内存分配机制
===

## 对象的创建
对象创建的主要过程：

![对象的创建](http://yuko.top:9099/images/2022/03/03/_20220304093702.png)

### 1. 类加载检查

虚拟机遇到一条 new 指令时，首先将会去检查这个指令的参数是否能在常量池中定位到一个类的符号引用，并且检查这个符号引用代表的类是否已经被加载、解析和
初始化过。如果没有，那必须先执行相应的类加载过程。

new 指令对应到语言层面上来说：new 关键词、对象克隆、对象序列化等。

### 2. 分配内存

在类加载检查通过后，接下来虚拟机将为新生对象分配内存。对象所需内存大小的在类加载完成后便可确定，为对象分配空间的任务等同于把一块确定大小的内存从 java
堆中划分出来。

这个步骤可能会出现的问题：
1. 如何划分内存。
2. 在并发情况下，可能出现正在给对象 A 分配内存，指针还没来得及修改，对象 B 又同时使用了原来的指针来分配内存的情况。

**划分内存的方法：**
* 指针碰撞(Bump the Pointer) 默认使用指针碰撞
如果 Java 堆中内存是绝对规整的，所有用过的内存都放在一边，空闲的内存放另一边，中间放着一个指针作为分界点的指示器，那所分配内存就仅仅是把那个指针向空闲
空间那边挪动一段与对象大小相等的距离。

* 空闲列表(Free List) 
如果 Java 堆中的内存并不是规整的，已使用的内存空间和空闲的内存相互交错，那就没有办法简单地进行指针碰撞了，虚拟机就必须维护一个列表，记录上哪些内存块
是可用的，在分配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的记录。

**解决并发问题的方法：**

* CAS (compare and swap)
虚拟机采用**CAS配上失败重试**的方式保证更新操作的原子性来对分配内存空间的动作进行同步处理。

* 本地线程分配缓冲(Thread Local Allocation Buffer, TLAB)
把内存分配的动作按照线程划分在不同的空间之中进行，即每个线程在 Java 堆中预先分配一小块内存。通过 `-XX：+/-UseTLAB`参数来设定虚拟机是否使用 TLAB
  (JVM 会默认开启-XX:+UseTLAB)，-XX:TLABSize指定 TLAB 的大小。

### 3. 初始化零值

内存分配完成后，虚拟机需要将分配到的内存空间都初始化零值(不包括对象头)，如果使用 TLAB，这一工作过程也可以提前至 TLAB 分配时进行。这一步操作保证了
对象的实例字段在 Java 代码中可以不赋初始值就直接使用，程序能访问到这些字段的数据类型所对应的零值。

### 4. 设置对象头

初始化零值之后，虚拟机要对对象进行必要设置，例如这个对象是哪个类的实例，如何才能找到类的元数据信息、对象的哈希码、对象的 GC 分代年龄等信息。这些信息
存放在对象的对象头之中。
在 HotSpot 虚拟机中，对象在内存中存储的布局可以分为 3 块区域：对象头(Header)、实例数据(Instance Data)和对齐填充(Padding)。HotSpot 虚拟机的对象头
包括两部分信息，第一部分是用于存储对象自身的运行时数据，如哈希码(HashCode)、GC分代年龄、锁状态标志、线程持有的锁、偏向线程 ID、偏向时间戳等。对象头
的另外一部分就是类型指针，即对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例。

**32位对象头**

![32位对象头](http://yuko.top:9099/images/2022/03/04/clipboard-3.png)

**64位对象头**

![64位对象头](http://yuko.top:9099/images/2022/03/04/clipboard-4.png)

### 5. 执行 \<init\> 方法

执行 init 方法，即对象按照程序员的意愿进行初始化，对应到语言层面上讲，就是为属性赋值。(注意，这与上面的赋零值不同，这是由程序员赋的值)，和执行构造方法。


### 对象大小和指针压缩

什么是 java 对象的**指针压缩**？
1. jdk1.6 update14 开始，在 64 bit 操作系统中，JVM 支持指针压缩。
2. jvm 配置参数: UseCompressedOops, compressed--压缩、oop(ordinary object pointer)--对象指针
3. 启用指针压缩: -XX:+UseCompressedOops(默认开启)，禁止指针压缩: -XX:-UseCompressedOops

为什么要进行指针压缩？
1. 在 64 位平台的 HotSpot 中使用 32 位指针(实际存储用 64 位)，内存使用会多出 1.5 倍左右，使用较大指针在主内存和缓存之间移动数据，**占用较大
宽带，同时 GC 也会承受较大压力。**
2. 为了减少 64 位平台下内存的消耗，启用指针压缩功能。
3. 在 JVM 中，32 位地址最大支持 4G 内存(2 的 32 次方)，可以通过对对象指针的存入**堆内存**时压缩编码、取出到**cpu寄存器**后解码方式进行优化(
对象指针在堆中是 32 位，在寄存器中是 35 位，2 的 35 次方 = 32G), 使得 jvm 只用 32 位地址就可以支持更大的内存配置(小于等于 32G)。
4. 堆内存小于 4G 时，不需要启用指针压缩，jvm 会直接去除 32 位地址，即使用低虚拟空间。
5. 堆内存大于 32G 时，压缩指针会失效，会强制使用 64 位(即 8 字节)来对 java 对象寻址，所以会出现 1 的问题，所以堆内存不要大于 32G 为好。

关于对象对齐: 对于大部分处理器，对象以 8 字节整数倍来对齐填充都是最高效的存取方式。


## 对象内存分配

![对象内存分配过程](http://yuko.top:9099/images/2022/03/07/327202027172.png)


### 对象栈上分配

通过 JVM 内存分配可以知道 JAVA 中的对象都是在堆上进行分配的，当对象没有引用的时候，需要依靠 GC 进行回收内存，如果对象数量较多的时候，会给 GC 带来较大
的压力，也间接影响了应用的性能。为了减少临时对象在堆内分配的数量，JVM 通过**逃逸分析**确定该对象不会被外部访问。如果不会逃逸可以将
该对象在**栈上分配**内存，这样该对象所占用的内存空间就可以随栈帧出栈而销毁，就减轻了垃圾回收的压力。

```
public User test1() {
    User user = new User();
    user.setId(1);
    user.setName("xiaoMing");
    return user;
}

public void test2() {
    User user = new User();
    user.setId(2);
    user.setName("xiaoWang");
}
```

**对象逃逸分析: **就是分析对象动态作用域，当一个对象在方法中被定义后，它可能被外部方法所引用，例如作为调用参数传递到其他地方中。
很显然 test1 方法中的 user 对象被返回了，这个对象的作用域范围不确定，test2 方法中的 user 对象可以确定当方法结束这个对象就可以认为是无效对象了，对于
这样的对象我们其实可以将其分配在栈内存里，让其在方法结束时跟随内存一起被回收掉。

JVM 对于这种情况可以通过开启逃逸分析参数(-XX:+DoEscapeAnalysis)来优化对象内存分配位置，使其通过**标量替换**优先分配在栈上(**栈上分配**)，**JDK7
之后默认开启逃逸分析**，如果要关闭使用参数(-XX:-DoEscapeAnalysis)

**标量替换:** 通过逃逸分析确定该对象不会被外部访问，并且对象可以被进一步分解时，**JVM不会创建该对象**，而是将该对象成员变量分解成若干个被这个方法使用
的成员变量所代替，这些代替的成员变量在栈帧或寄存器上分配空间，这样就不会因为没有一大块连续空间而导致对象内存不够分配。开启标量替换参数(-XX:+EliminateAllocations)
**JDK7之后默认开启。**

**标量和聚合量:** 标量即不可被进一步分解的量，而 JAVA 的基本数据类型就是标量(如：int, long 等基本数据类型以及 reference 类型等)，标量的对立就是
可以被进一步分解的量，而这种量被称为聚合量。而在 JAVA 中对象就是可以被进一步分解的聚合量。

栈上分配示例:

```
/**
 * 栈上分配，标量替换
 * 代码调用了1亿次alloc()，如果分配到堆上，大概需要1GB以上的堆空间，如果堆空间小于该值，必然会触发GC。
 *
 * 使用如下参数会发生少量 GC
 * -Xmx15m -Xms15m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations
 * 使用如下参数都会发生大量 GC
 * -Xmx15m -Xms15m -XX:-DoEscapeAnalysis -XX:+PrintGC -xx:+EliminateAllocations
 * -Xmx15m -Xms15m -XX:+DoEscapeAnalysis -XX:+PrintGC -xx:-EliminateAllocations
 */
 public class AllotOnStack {
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
    
    public static void alloc() {
        User user = new User();
        user.setId(1);
        user.setName("xiaoMing");
    }
 }
```

结论：**栈上分配依赖于逃逸分析和标量替换，**


### 对象在 Eden 区分配

大多数情况下，对象在新生代中的 Eden 区分配。当 Eden 区没有足够空间进行分配时，虚拟机将发起一次 Minor GC。

** Minor GC 和 Full GC 的区别?**
* Minor GC / Young GC: 指发生新生代的垃圾收集动作，Minor GC 非常频繁，回收速度一般也比较快。
* Major GC / Full GC: 一般会回收老年代，年轻代，方法区的垃圾，Major GC 一般比 Minor GC 慢上 10倍以上。

**Eden 与 Survivor 区默认为 8:1:1。** 大量的对象被分配在 eden 区，eden 区满了会触发 Minor GC，可能会有 99% 以上的对象成为垃圾被回收掉，剩余
存活的对象会被挪到空的那块 Survivor 区，下一次 eden 区满了后又会触发 minor GC，把 eden 区和 survivor 区垃圾对象回收，把剩余存活的对象一次性
挪动到另外一块为空的 Survivor 区，因为新生代的对象都是朝生夕死的，存活时间很短，所以 JVM 默认的比例为8:1:1是很合适的，**让 eden 区尽量地大，Survivor
够用即可，** JVM 默认有这个参数-XX:+UseAdaptiveSizePolicy(默认开启)，会导致这个 8:1:1 比例自动变化，如果不想这个比例有变化可以设置参数
-XX:-UseAdaptiveSizePolicy。

示例：
```
// 添加运行 JVM 参数：-XX:+PrintGCDetails
public class GCTest {

    public static void main(String[] args) {
        byte[] allocation1 = new byte[49500 * 1024];
    }
}

// 结果:
Heap
 PSYoungGen      total 66560K, used 57344K [0x0000000776100000, 0x000000077ab00000, 0x00000007c0000000)
  eden space 57344K, 100% used [0x0000000776100000,0x0000000779900000,0x0000000779900000)
  from space 9216K, 0% used [0x000000077a200000,0x000000077a200000,0x000000077ab00000)
  to   space 9216K, 0% used [0x0000000779900000,0x0000000779900000,0x000000077a200000)
 ParOldGen       total 151552K, used 0K [0x00000006e2200000, 0x00000006eb600000, 0x0000000776100000)
  object space 151552K, 0% used [0x00000006e2200000,0x00000006e2200000,0x00000006eb600000)
 Metaspace       used 3411K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 373K, capacity 388K, committed 512K, reserved 1048576K
  
// 从这里可以看出 eden 区内存几乎被分配完全，如果此时在创建一个对象会发生什么？
public class GCTest {

    public static void main(String[] args) {
        byte[] allocation1 = new byte[49500 * 1024];
        byte[] allocation2 = new byte[600 * 1024];
    }
}

// 结果:
Heap
 PSYoungGen      total 66560K, used 2809K [0x0000000776100000, 0x000000077e300000, 0x00000007c0000000)
  eden space 57344K, 2% used [0x0000000776100000,0x0000000776225748,0x0000000779900000)
  from space 9216K, 17% used [0x0000000779900000,0x0000000779a98d38,0x000000077a200000)
  to   space 9216K, 0% used [0x000000077da00000,0x000000077da00000,0x000000077e300000)
 ParOldGen       total 151552K, used 49508K [0x00000006e2200000, 0x00000006eb600000, 0x0000000776100000)
  object space 151552K, 32% used [0x00000006e2200000,0x00000006e5259010,0x00000006eb600000)
 Metaspace       used 3416K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 373K, capacity 388K, committed 512K, reserved 1048576K

```
简单解释发生的现象：因为给 allocation2 分配内存的时候 eden 区内存几乎被分配完了，当 Eden 没有足够空间进行分配时，虚拟机将发起一次 Minor GC，
GC 期间有发现一次 Minor GC，GC 期间又发现 allocation1 无法存入 Survivor 空间，所以只好把新生代的对象**提前转移到老年代**中去，老年代上的空间
足够存放 allocation1，所以不会发生 Full GC。执行 Minor GC 后，后面分配的对象如果能够存在 eden 区的话，还是会继续放在 eden 区的。

### 大对象直接进入老年代

大对象就是需要大量连续内存空间的对象(比如: 字符串、数组)。JVM 参数 -XX:PretenureSizeThreshold 可以设置大对象的大小，超过此大小的对象将直接进入
老年代，不会进入年轻代，这个参数只在 Serial 和 ParNew 两个收集器下有效。这样做的目的是为了避免为大对象分配内存时的复制操作而降低效率。

### 长期存活的对象将进入老年代

既然虚拟机采用分代收集的思想来管理内存，那么内存回收时就必须能识别哪些对象应放在新生代，哪些对象应放在老年代。为了做到这一点，虚拟机给每一个对象都设置了
一个对象年龄(Age)计数器。

如果对象在 Eden 区出生并经过第一次 Minor GC 后仍然存活，并且能够被 Survivor 区容纳的话，将被移动到 Survivor 区中，并将其对象年龄设为 1。
对象在 Survivor 中每熬过一个 Minor GC，年龄就增加一岁，当它的年龄增加到一定程度(默认为15岁，CMS收集器默认为6岁，不同的收集器会略微不同)，就会
晋升到老年代中，对象晋升到老年代的年龄阀值，可以通过参数 `-XX:MaxTenuringThreshold` 来设置。

### 对象动态年龄判断

当前放对象的 Survivor 区域里，一批对象的总大小大于这块 Survivor 区域内存大小的 50%(-XX:TargetSurvivorRatio可以指定)，那么此时大于等于
这批对象年龄最大值的对象，就可以直接进入老年代了，比如 Survivor 区域里现在有一批对象，年龄1 + 年龄2 + 年龄n的多个年龄对象总和超过了 Survivor 区域
的50%，此时就会把年龄n(含)以上的对象都放入老年代。这个规则其实是希望那些可能是长期存活的对象，尽早进入老年代。**对象动态年龄判断机制一般在 minor gc
之后触发的**

### 老年代空间分配担保机制

年轻代每次 minor gc 之前都会计算老年代剩余可用空间。如果这个可用空间大小小于年轻代里所有对象之和(包括垃圾对象)，此时会去检查一个参数 
`-XX:-HandlePromotionFailure` 的参数是否设置了(JDK1.8默认设置了)，如果有这个参数，就会用老年代剩余内存大小与先前每一个 Minor GC 后进入
老年代的对象的平均大小进行比较。如果上一步结果是小于或者之前说的参数没有设置，那么就会触发一次 Full GC，对年老代和年轻代一起回收一次垃圾，如果回收完
还是没有足够的空间，存放新的对象就会发生 OOM。当然，如果 Minor GC 之后剩余存活的需要挪动到老年代的对象大小还是大于老年代可用空间，那么也会触发 
Full GC，Full GC 完以后如果还是没有空间存放 Minor GC 之后的存活对象，则也会发生 OOM。

![老年代空间分配担保机制](http://yuko.top:9099/images/2022/03/08/clipboard-5.png)


## 对象内存回收

堆中几乎存放着所有的实例对象，对堆垃圾回收前的第一步就是要判断哪些对象已经死亡(即不能再被任何途径使用的对象)。

### 引用计数法

给对象中添加一个引用计数器，每当有一个地方引用它，计数器就加1；当引用失效时，计数器就减1；任何计数为0的对象就不能再被使用的。
**这个方法实现简单，效率高。但是现在主流虚拟机都没有使用这个方法来管理内存，主要原因是它很难解决对象之间循环引用的问题。**

```
public class ReferenceCountingGc{

    Object instance = null;
    
    public static void main(Stirng[] args) {
        ReferenceCountingGc objA = new ReferenceCountingGc();
        ReferenceCountingGc objB = new ReferenceCountingGc();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
    }
}
```

### 可达性分析算法

将 `GC Roots`对象作为起点，从这些节点开始向下搜索引用的对象，找到的对象都标记为 `非垃圾对象`，其余未标记的对象都称为垃圾对象。
GC Roots 根节点: 线程栈的本地变量、静态变量、本地方法栈的变量等。

![可达性分析算法](http://yuko.top:9099/images/2022/03/08/6E72848C54E84BB7A76F0FF89ED5010D.jpg)


### 如何判断一个类是无用的类

方法区主要回收的是无用的类，那么如何判断一个类是无用的类呢？类需要满足3个条件才能算是"无用的类":
* 该类所有对象实例都已经被回收，也就是说堆中不存在该类的任何实例。
* 加载该类的 ClassLoader 已经被回收。
* 该类对应的 java.lang.Class 对象没有在任何地方被引用，无法在任何地方通过反射访问到该类的方法。





