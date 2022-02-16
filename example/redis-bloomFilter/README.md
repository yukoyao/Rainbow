#### Redis BloomFilter 的使用

##### 什么是 BloomFilter ?

布隆过滤器是由一个初值都为零的 bit 数组和多个哈希函数构成，用于快速判断某个数据是否存在的，但是存在误差。

##### BloomFilter 的特点？

* 高效地插入和查询，占用空间少。
* 一个元素如果判断结果为存在的时候元素不一定存在，如果判断结果为不存在的时候则一定不存在。
* 可以添加元素，但是不允许删除元素。因为删除元素会增加误判率。
* 误判只会存在于未在过滤器中存在的元素，对于存在过的元素不会存在误判。


##### BloomFilter 过滤器实现原理和数据结构

布隆过滤器(Bloom Filter)是一种专门用来解决去重问题的高级数据结构，本质上是由一个大型位数组和几个不同的无偏 hash 函数(无偏表示分布均匀)。
有一个初值都为零的 bit 数组和多个哈希函数构成，用来判断某个数据是否存在。跟 HyperLogLog 一样有一点不精确，也存在一定的误判率。

当有变量被加入集合时，通过 N 个映射函数将这个变量映射成位图中的 N 个点，把它们置为 1。查询某个变量的时候只要看看位图上对应的点是否为 1，就可以
大概率地知道集合中存不存在该元素。注意必须所有对应的点都为 1 才有概率存在，其中一个点为 0 就表示一定不存在。(大概率存在的原因是因为是散列函数存在碰撞)


##### BloomFilter 使用步骤

1. 初始化

布隆过滤器本质上是由长度为 m 的位向量或位列表(仅包含 0 或 1 位值的列表)组成，最初所有的值均设置为 0。

2. 添加

当我们向布隆过滤器添加元素的时候，为了尽量不冲突，会使用多个 hash 函数对 key 进行运算，算得一个下标索引值，然后对位数组长度
进行取模运算得到一个位置，每个 hash 函数都会算得一个不同的位置，再把位数组的这几个位置都置为 1 就完成了 add 操作。

3. 判断是否存在

向布隆过滤器查询某个 key 是否存在时，先把这个 key 通过相同的多个 hash 函数进行运算，查询对应的位置是否都为 1， 只要有一个
位为 0，那么说明布隆过滤器这个 key 不存在；如果这几个位置都为 1，那么极有可能存在；因为这些位置的 1 可能是因为其他 key 存在
导致的(也就是 hash 冲突)。


> 使用布隆过滤器时最好不要让实际元素数量远大于初始化数量。如果超过了，应该对布隆过滤器进行重建，重新分配一个 size 更大的过滤器，再将所有的历史元素批量 add 进去

##### BloomFilter 的优缺点？

###### 优点
1. 相比于其他数据结构，布隆过滤器在空间和时间方面都有巨大的优势。布隆过滤器插入和查询时间都是常数(O(k)).另外，散列函数相互之间
没有关系，方便由硬件并行实现。布隆过滤器不需要存储元素本身，在某些保密要求严格的场合有优势。
2. 使用相同散列函数的两个布隆过滤器的交并运算可以使用位操作进行

###### 缺点
1. 随着存入的元素数量增加，误差率也会随之增加。但是如果元素数量太少，使用散列表足矣。
2. 一般情况下不能从布隆过滤器中删除元素，删除元素会增加误算率。

##### 多种方式实现布隆过滤器

1. Guava 布隆过滤器解决缓存穿透(只能单机使用)
2. Redis 布隆过滤器解决缓存穿透(分布式)


##### 布隆过滤器的应用场景

1. 缓存穿透(Basic)
2. 白名单

白名单里面有的才让通过，没有直接返回。注意所有 key 都需要往 redis 和 bloomFilter 里面放入。

4. 黑名单 

让布隆过滤器当作黑名单使用，解决类似抖音防止推荐重复视频、饿了么防止推荐重复优惠券，推荐的时候先去布隆过滤器中进行查询。
如果已经存在于黑名单之中，则不重复推荐；如果是新视频，则推荐给新用户并更新布隆过滤器，