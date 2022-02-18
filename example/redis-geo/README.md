### 使用 Redis GEO 实现附近商家

##### GeoHash 核心原理解析

简单来说：将二维的点数据转换成一维的数据。

##### 基本命令

* geoadd: 用于存储指定的地理空间位置。

```
// GEOADD key longitude latitude member [longitude latitude member ...]
GEOADD Sicily 13.361389 38.115556 "Palermo" 15.087269 37.502669 "Catania"
```

* geopos: 用于从给定的 key 里返回所有指定名称的(member)的位置(经度和纬度)，不存在返回 nil

```
// GEOPOS key member [member...]
GEOPOS Sicily Palermo Catania
```

* geodist: 用于返回两个给定位置之间的距离。[m: 米(默认单位), km: 千米, mi: 英里, ft: 英尺]

```
// GEODIST key member1 member2 [m|km|ft|mi]
GEODIST Sicily Palermo Catania km
```

* georadius、georadiusbymember 
georadius 以给定的经纬度为中心，返回键包含的位置元素当中，与中心的距离不超过给定最大距离的所有位置元素。
georadiusbymember 与 georadius 类似，但是不是根据给定经纬度，而是由给定的元素决定。

```
GEORADIUS key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREIDST key]
GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC] [STORE key] [STOREIDST key]
```

参数说明:
1. WITHDIST: 在返回位置元素的同时，将位置元素与中心之间的距离也一并返回。
2. WITHCOORD: 将位置元素的经度和纬度也一并返回。
3. COUNT: 限定返回的记录数。
4. ASC: 查找结果根据距离从近到远排序。
5. DESC: 查找结果根据远到近排序。

* geohash: 用于获取一个或多个位置元素的 geohash 值。

```
GEOHASH key member [member ...]
```
