package org.rainbow.example.controller;

import org.rainbow.example.j2cache.annotation.Cache;
import org.rainbow.example.j2cache.annotation.CacheEvictor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: K
 * @date: 2022/03/21 16:17
 */
@RestController
@RequestMapping("/annotation")
public class J2CacheController {

  /**
   * 从j2cache中获取缓存数据
   *
   * @return
   */
  @GetMapping("/getCacheData/{id}")
  @Cache(region = "rx", key = "user", params = "id")
  public List<String> getCacheData(@PathVariable(name = "id") String id) {
    //没有获取到缓存数据，需要从数据库中查询数据
    List<String> data = new ArrayList<>();
    data.add("beijing");
    data.add("nanjing");
    data.add("shanghai");
    System.out.println("查询数据库");
    return data;
  }

  /**
   * 从j2cache中获取缓存数据
   *
   * @return
   */
  @GetMapping("/getCacheDataBody")
  @Cache(region = "rx", key = "user", params = "0.name")
  public List<Map> getCacheDataBody(@RequestBody Map body) {
    //没有获取到缓存数据，需要从数据库中查询数据
    List<Map> data = new ArrayList<>();
    data.add(body);
    System.out.println("查询数据库");
    return data;
  }

  /**
   * 从j2cache中获取缓存数据
   *
   * @return
   */
  @GetMapping("/getCacheDataParams")
  @Cache(region = "rx", key = "user", params = "0.name")
  public List<Map> getCacheDataParams(@RequestParam Map params) {
    //没有获取到缓存数据，需要从数据库中查询数据
    List<Map> data = new ArrayList<>();
    data.add(params);
    System.out.println("查询数据库");
    return data;
  }

  @GetMapping("/getAllData")
  @Cache(region = "rx", key = "users")
  public List<String> getAllData() {
    //没有获取到缓存数据，需要从数据库中查询数据
    List<String> data = new ArrayList<>();
    data.add("beijing");
    data.add("nanjing");
    data.add("shanghai");
    System.out.println("查询数据库");
    return data;
  }

  /**
   * 清理指定缓存数据
   *
   * @return
   */
  @CacheEvictor({@Cache(region = "rx", key = "user", params = "id")})
  @GetMapping("/evict/{id}")
  public String evict(@PathVariable(name = "id") String id) {
    System.out.println("删除数据库");
    return "evict success";
  }

  /**
   * 清理指定缓存数据
   *
   * @return
   */
  @CacheEvictor({@Cache(region = "rx", key = "users")})
  @GetMapping("/evict")
  public String evictAll() {
    System.out.println("删除数据库");
    return "evict success";
  }
}
