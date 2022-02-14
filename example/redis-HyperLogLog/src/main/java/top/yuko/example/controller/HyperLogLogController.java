package top.yuko.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuko.example.service.HyperLogLogService;

/**
 * UV统计 Controller
 *
 * @author: K
 * @date: 2022/02/09 11:27
 */
@RestController
public class HyperLogLogController {

  @Autowired
  private HyperLogLogService hyperLogLogLogService;


  /**
   * 当日 PV
   *
   * @return pv
   */
  @GetMapping("/dailyPv")
  public Long dailyPv() {
    return this.hyperLogLogLogService.dailyPv();
  }

  /**
   * 本周 PV
   *
   * @return pv
   */
  @GetMapping("/weeklyPv")
  public Long weeklyPv() {
    return this.hyperLogLogLogService.weeklyPv();
  }

  /**
   * 本月 PV
   *
   * @return pv
   */
  @GetMapping("/monthlyPv")
  public Long monthlyPv() {
    return this.hyperLogLogLogService.monthlyPv();
  }

}
