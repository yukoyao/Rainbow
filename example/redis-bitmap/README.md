### 使用 redis bitmap 数据结构实现签到功能

##### 实现功能

1. 进行签到
2. 获取月签到天数
3. 获取月签到连续天数
4. 获取月签到情况

##### 常见的统计类型

* 聚合统计: 统计多个集合元素的聚合结果。交差并。
* 排序统计: 类似获取抖音最新评论留言的场景。
* 二值统计: 类似签到打卡的场景。
* 基数统计: 统计一个集合中不重复的元素。

##### Redis 类型 bitmap

bitmap: 由 0 和 1 状态表现的二进制位的 bit 数组。

用于处理: ① 电影、广告是否被点击播放过  ② 打卡、签到统计

##### Redis bitmap 基本命令

1. setbit
```
bitmap 的 offset 偏移量是从零开始算。value 只能是 0 和 1。
setbit key offset value
```

2. getbit
```
getbit key offset
```

3. strlen 统计字节数占用多少
4. bitcount 全部键里面含有 1 的有多少?



