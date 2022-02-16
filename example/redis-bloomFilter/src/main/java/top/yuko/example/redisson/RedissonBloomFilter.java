package top.yuko.example.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 布隆过滤器
 *
 * @author: K
 * @date: 2022/02/16 15:20
 */
public class RedissonBloomFilter {

  public static final int _1W = 10000;

  // 布隆过滤器预计要插入的数据量
  public static int size = 100 * _1W;

  // 误判率。并不是越小越好，误判率意味着需要更多的哈希函数和更多的位空间。
  public static double fpp = 0.03;

  static RedissonClient redissonClient = null;

  static RBloomFilter<String> rBloomFilter = null;

  static {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://192.168.110.100:6379").setDatabase(0);
    // 构建 redisson
    redissonClient = Redisson.create(config);
    // 通过 redisson 构建 BloomFilter
    rBloomFilter = redissonClient.getBloomFilter("phoneListBloomFilter", new StringCodec());
    rBloomFilter.tryInit(size, fpp);
    rBloomFilter.add("10086");
    redissonClient.getBucket("10086", new StringCodec()).set("chinaMobile10086");
  }


  public static void main(String[] args) {
    String phoneListById = getPhoneListById("10087");
    System.out.println("------查询出来的结果： " + phoneListById);
    //暂停几秒钟线程
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    redissonClient.shutdown();
}

  private static String getPhoneListById(String IDNumber) {
    String result = null;
    if (IDNumber == null) {
      return null;
    }
    // 1. 先去布隆过滤器查询
    if (rBloomFilter.contains(IDNumber)) {

      // 2. 布隆过滤器有 再去 redis 里面查询
      RBucket<String> rBucket = redissonClient.getBucket(IDNumber, new StringCodec());
      result = rBucket.get();
      if (result != null) {
        return "come from redis: " + result;
      } else {
        // 3. redis 里也没有 去 mysql 里面查询
        result = getPhoneListByMySQL(IDNumber);

        if (result != null) {
          // 重新将数据更新回 redis
          redissonClient.getBucket(IDNumber, new StringCodec()).set(result);
          return "come from mysql: " + result;
        }
      }
    }
    return result;
  }

  private static String getPhoneListByMySQL(String IDNumber) {
    return "chinaMobile" + IDNumber;
  }
}
