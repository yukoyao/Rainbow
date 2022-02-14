package top.yuko.example.service;

/**
 * Redis TypeLogLog Service
 *
 * @author: K
 * @date: 2022/02/09 11:28
 */
public interface HyperLogLogService {

  /**
   * 获取日 pv
   *
   * @return pv
   */
  Long dailyPv();

  /**
   * 获取本周 pv
   *
   * @return pv
   */
  Long weeklyPv();

  /**
   * 获取本月 pv
   *
   * @return pv
   */
  Long monthlyPv();
}
