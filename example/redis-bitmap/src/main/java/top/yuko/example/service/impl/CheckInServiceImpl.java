package top.yuko.example.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.BitFieldSubCommands.BitFieldSubCommand;
import org.springframework.data.redis.connection.BitFieldSubCommands.BitFieldType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.yuko.example.service.CheckInService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签到 Service 实现
 *
 * @author: K
 * @date: 2022/02/07 09:17
 */
@Service
public class CheckInServiceImpl implements CheckInService {

  /**
   * 签到 key USER_CHECK_IN_:{userId}:{date}
   */
  private static final String CHECK_IN_PRE_KEY = "USER_CHECK_IN:{}:{}";

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  /**
   * 是否进行过签到
   *
   * @param userId 用户 id
   * @return true / false
   */
  public Boolean checkIn(final Long userId) {
    String today = DateUtil.format(new Date(), DatePattern.SIMPLE_MONTH_PATTERN);
    int dayOfMonth = DateUtil.dayOfMonth(new Date());

    if (isCheckIn(userId, today, (long) (dayOfMonth - 1))) {
      return false;
    }

    stringRedisTemplate.opsForValue().setBit(getCheckInKey(today, userId), dayOfMonth - 1, true);
    return true;
  }

  /**
   * 获取某人的月签到情况
   *
   * @param userId 用户 id
   * @return 签到情况 map
   */
  @Override
  public Map<Integer, Boolean> getCheckInMonthly(final Long userId) {
    long startTime = System.currentTimeMillis();
    Map<Integer, Boolean> result = new HashMap<>();
    String date = DateUtil.format(new Date(), DatePattern.SIMPLE_MONTH_PATTERN);
    int end = DateUtil.endOfMonth(new Date()).dayOfMonth();
    for (int start = 0; start <= end; start++) {
      result.put(start, stringRedisTemplate.opsForValue().getBit(getCheckInKey(date, userId), start));
    }
    long endTime = System.currentTimeMillis();
    System.out.println(StrUtil.format("耗时：{} ms", (endTime - startTime)));
    return result;
  }

  /**
   * 获取某人的月签到天数
   *
   * @param userId 用户 id
   * @return 签到天数
   */
  @Override
  public Long getCheckInCountMonthly(final Long userId) {
    String date = DateUtil.format(new Date(), DatePattern.SIMPLE_MONTH_PATTERN);
    String checkInKey = getCheckInKey(date, userId);
    return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(checkInKey.getBytes()));
  }

  /**
   * 获取某人的月连续签到天数
   *
   * @param userId 用户 id
   * @return 签到天数
   */
  @Override
  public Long getConsecutiveCheckInCountMonthly(final Long userId) {
    long checkInCount = 0;

    int offset = DateUtil.dayOfMonth(new Date());
    String date = DateUtil.format(new Date(), DatePattern.SIMPLE_MONTH_PATTERN);
    String checkInKey = getCheckInKey(date, userId);

    BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create()
        .get(BitFieldType.unsigned(offset)).valueAt(0);
    List<Long> list = stringRedisTemplate
        .execute((RedisCallback<List<Long>>) con -> con.bitField(checkInKey.getBytes(), bitFieldSubCommands));

    long v = CollUtil.isEmpty(list) ? 0 : list.get(0);
    for (int i = 0; i < offset; i++) {
      if (v >> 1 << 1 == v) {   // 先右移再左移 如果相同代表低位为 0
        // 低位为0且非当天说明连续签到中断了
        if (i > 0) {
          break;
        }
      } else {
        checkInCount += 1;
      }
      v >>= 1;
    }
    return checkInCount;
  }

  /**
   * 判断某个用户在某个时间是否进行过签到
   *
   * @param userId 用户 id
   * @param date   时间字符串
   * @param offset 签到 key 偏移
   * @return true / false
   */
  private Boolean isCheckIn(Long userId, String date, Long offset) {
    return stringRedisTemplate.opsForValue().getBit(getCheckInKey(date, userId), offset);
  }

  /**
   * 获取签到 Redis Key
   *
   * @param date   日期
   * @param userId 用户 id
   * @return redis key
   */
  private String getCheckInKey(String date, Long userId) {
    return StrUtil.format(CHECK_IN_PRE_KEY, userId, date);
  }
}
