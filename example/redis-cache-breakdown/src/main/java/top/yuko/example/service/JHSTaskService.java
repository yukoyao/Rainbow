package top.yuko.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.yuko.example.constant.Constants;
import top.yuko.example.entities.Product;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: K
 * @date: 2022/02/21 09:40
 */
@Service
@Slf4j
public class JHSTaskService {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  //@PostConstruct
  public void initJHS() {
    new Thread(() -> {
      // 模拟定时器 定时将数据库的特价商品刷新到 redis 中
      while (true) {
        // 模拟从数据库读取100件特价商品，用于加载到聚划算的页面中
        List list = this.products();
        // 采用 Redis list 数据结构的 lpush 来实现存储
        this.redisTemplate.delete(Constants.JHS_KEY);
        this.redisTemplate.opsForList().leftPushAll(Constants.JHS_KEY, list);
        // 间隔一分钟执行一遍
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  @PostConstruct
  public void initJHSBest() {
    new Thread(() -> {
      //模拟定时器，定时把数据库的特价商品，刷新到redis中
      while (true) {
        //模拟从数据库读取100件特价商品，用于加载到聚划算的页面中
        List list=this.products();
        //先更新B缓存
        this.redisTemplate.delete(Constants.JHS_KEY_B);
        this.redisTemplate.opsForList().leftPushAll(Constants.JHS_KEY_B,list);
        this.redisTemplate.expire(Constants.JHS_KEY_B,20L,TimeUnit.DAYS);

        //再更新A缓存
        this.redisTemplate.delete(Constants.JHS_KEY_A);
        this.redisTemplate.opsForList().leftPushAll(Constants.JHS_KEY_A,list);
        this.redisTemplate.expire(Constants.JHS_KEY_A,15L,TimeUnit.DAYS);

        //间隔一分钟 执行一遍
        try { TimeUnit.MINUTES.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
      }
    }).start();
  }


  public List products() {
    List list = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      Random rand = new Random();
      int id = rand.nextInt(10000);
      Product product = new Product(id, "product" + i, i, "detail");
      list.add(product);
    }
    return list;
  }
}

