package org.rainbow.example.controller;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

/**
 * J2Cache 基本使用 Controller
 *
 * @author: K
 * @date: 2022/03/19 15:14
 */
@RestController
@RequestMapping("/basic")
public class BasicUseController {

  @Autowired
  private CacheChannel cacheChannel;

  @GetMapping("/getUserInfo")
  public List<String> getUserInfo() {

    String region = "user";
    String key = "user0001";

    CacheObject cacheObject = cacheChannel.get(region, key);
    if (cacheObject.getValue() == null) {
      // 缓存中没有找到 将从数据库中获取
      List<String> dbReturnList = new ArrayList<>();
      dbReturnList.add("info1");
      dbReturnList.add("info2");
      // 放入缓存
      cacheChannel.set(region, key, dbReturnList);
      return dbReturnList;
    }
    return (List<String>) cacheObject.getValue();
  }

  @GetMapping("/evict")
  public String evict(String region, String key) {
    cacheChannel.evict(region, key);
    return "evict success";
  }

  @GetMapping("/check")
  public String check(String region, String key) {
    int check = cacheChannel.check(region, key);
    return "level:" + check;
  }

  @GetMapping("/exists")
  public String exists(String region, String key) {
    boolean exists = cacheChannel.exists(region, key);
    return "exists:" + exists;
  }

  @GetMapping("/clear")
  public String clear(String region) {
    cacheChannel.clear(region);
    return "clean success";
  }
}
