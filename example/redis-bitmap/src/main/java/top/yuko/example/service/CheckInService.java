package top.yuko.example.service;

import java.util.Map;

/**
 * 签到 Service
 *
 * @author: K
 * @date: 2022/02/07 09:16
 */
public interface CheckInService {

  /**
   * 进行签到
   *
   * @param userId 用户 id
   * @return true / false
   */
  Boolean checkIn(Long userId);

  /**
   * 获取某人的月签到情况
   *
   * @param userId 用户 id
   * @return 签到情况 map
   */
  Map<Integer, Boolean> getCheckInMonthly(Long userId);

  /**
   * 获取某人的月签到天数
   *
   * @param userId 用户 id
   * @return 签到天数
   */
  Long getCheckInCountMonthly(Long userId);

  /**
   * 获取某人的月连续签到天数
   *
   * @param userId 用户 id
   * @return 签到天数
   */
  Long getConsecutiveCheckInCountMonthly(Long userId);
}
