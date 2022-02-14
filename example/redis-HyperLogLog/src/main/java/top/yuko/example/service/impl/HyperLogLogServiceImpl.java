package top.yuko.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.yuko.example.service.HyperLogLogService;

/**
 * Redis HyperLogLog Service 实现
 *
 * @author: K
 * @date: 2022/02/09 11:29
 */
@Service
public class HyperLogLogServiceImpl implements HyperLogLogService {

  private static final String USER_LOGIN_PRE_KEY = "USER:LOGIN:";

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  /**
   * 获取日 pv
   *
   * @return pv
   */
  @Override
  public Long dailyPv() {
    // 当日开始时间戳 和 结束时间戳
    List<DateTime> dateTimeList = DateUtil.rangeToList(DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(new Date()),
        DateField.MINUTE);
    List<String> keyList = addPrefix(dateTimeList);
    return redisTemplate.opsForHyperLogLog().size(ArrayUtil.toArray(keyList, String.class));
  }

  /**
   * 获取本周 pv
   *
   * @return pv
   */
  @Override
  public Long weeklyPv() {
    List<DateTime> dateTimeList = DateUtil.rangeToList(DateUtil.beginOfWeek(new Date()), DateUtil.endOfWeek(new Date()),
        DateField.MINUTE);
    List<String> keyList = addPrefix(dateTimeList);
    return redisTemplate.opsForHyperLogLog().size(ArrayUtil.toArray(keyList, String.class));
  }

  /**
   * 获取本月 pv
   *
   * @return pv
   */
  @Override
  public Long monthlyPv() {
    List<DateTime> dateTimeList = DateUtil.rangeToList(DateUtil.beginOfMonth(new Date()), DateUtil.endOfMonth(new Date()),
        DateField.MINUTE);
    List<String> keyList = addPrefix(dateTimeList);
    return redisTemplate.opsForHyperLogLog().size(ArrayUtil.toArray(keyList, String.class));
  }

  /**
   * 给时间戳列表添加前缀
   *
   * @param dateTimeList 时间戳列表
   * @return 添加后的字符串列表
   */
  private List<String> addPrefix(List<DateTime> dateTimeList) {
    List<String> result = new ArrayList<>();
    if (CollUtil.isNotEmpty(dateTimeList)) {
      dateTimeList.forEach(date -> {
        String dateStr = DateUtil.format(date, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        result.add(getKey(dateStr));
      });
    }
    return result;
  }

  /**
   * 获取 Redis Key
   *
   * @param date 时间
   * @return key
   */
  private String getKey(String date) {
    return USER_LOGIN_PRE_KEY + date;
  }

  @PostConstruct
  public void init() {
    // hyperLogLog 键设计到分 202202101133
    // 随机生成本月内的时间戳 还有随机 IP 地址
    final List<DateTime> dateTimeList = DateUtil.rangeToList(
        DateUtil.beginOfMonth(new Date()), DateUtil.endOfMonth(new Date()), DateField.SECOND);

    ThreadUtil.execAsync(() -> {
      for (int i = 0; i < 1000000; i++) {
        DateTime next = RandomUtil.randomEle(dateTimeList);
        String key = getKey(DateUtil.format(next, DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
        String randomIp = StrUtil.format("{}.{}.{}.{}", RandomUtil.randomInt(1, 999),
            RandomUtil.randomInt(1, 999), RandomUtil.randomInt(1, 999), RandomUtil.randomInt(1, 999));
        redisTemplate.opsForHyperLogLog().add(key, randomIp);
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }

}
