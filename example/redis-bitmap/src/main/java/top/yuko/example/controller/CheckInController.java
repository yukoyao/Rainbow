package top.yuko.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuko.example.service.CheckInService;
import java.util.Map;

/**
 * 签到 Controller
 *
 * @author: K
 * @date: 2022/02/04 16:05
 */
@RestController
public class CheckInController {

  @Autowired
  private CheckInService checkInService;

  /**
   * 进行签到
   *
   * @param userId 用户 id
   * @return true / false
   */
  @GetMapping("/checkIn")
  public Boolean checkIn(Long userId) {
    return this.checkInService.checkIn(userId);
  }

  /**
   * 获取某人的月签到情况
   *
   * @param userId 用户 id
   * @return 签到情况 map
   */
  @GetMapping("/getMonthlyCheckIn")
  public Map<Integer, Boolean> getCheckInMonthly(Long userId) {
    return this.checkInService.getCheckInMonthly(userId);
  }

  /**
   * 获取某人的月签到天数
   *
   * @param userId 用户 id
   * @return 签到天数
   */
  @GetMapping("/getCheckInCountMonthly")
  public Long getCheckInCountMonthly(Long userId) {
    return this.checkInService.getCheckInCountMonthly(userId);
  }

  /**
   * 获取某人的月连续签到天数
   *
   * @param userId 用户 id
   * @return 签到天数
   */
  @GetMapping("/getConsecutiveCheckInCountMonthly")
  public Long getConsecutiveCheckInCountMonthly(Long userId) {
    return this.checkInService.getConsecutiveCheckInCountMonthly(userId);
  }

}
