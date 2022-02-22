package top.yuko.example.controller;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import top.yuko.example.constant.Constants;
import top.yuko.example.entities.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: K
 * @date: 2022/02/21 16:22
 */
@Slf4j
@Api("商品列表接口")
@RestController
public class JHSProductController {

  @Autowired
  private RedisTemplate redisTemplate;

  @ApiOperation(value = "分页查看商品列表")
  @RequestMapping(value = "/product/find", method = RequestMethod.GET)
  public List<Product> find(int page, int size) {
    List<Product> result = new ArrayList<>();
    long start = (long) (page - 1) * size;
    long end = start + size - 1;
    try {
      //采用redis list数据结构的 lrange 命令实现分页查询
      result = this.redisTemplate.opsForList().range(Constants.JHS_KEY, start, end);
      if (CollUtil.isEmpty(result)) {
        //TODO 走DB查询
      }
      log.info("查询结果：{}", result);
    } catch (Exception ex) {
      //这里的异常，一般是redis瘫痪 ，或 redis网络timeout
      log.error("exception:", ex);
      //TODO 走DB查询
    }
    return result;
  }

  @ApiOperation(value = "分页查看商品列表")
  @RequestMapping(value = "/product/findBest", method = RequestMethod.GET)
  public List<Product> findBest(int page, int size) {
    List<Product> result = new ArrayList<>();
    long start = (long) (page - 1) * size;
    long end = start + size - 1;
    try {
      //采用redis list数据结构的 lrange 命令实现分页查询
      result = this.redisTemplate.opsForList().range(Constants.JHS_KEY_A, start, end);
      if (CollUtil.isEmpty(result)) {
        log.info("=========A缓存已经失效了，记得人工修补，B缓存自动延续5天");
        //用户先查询缓存A(上面的代码)，如果缓存A查询不到（例如，更新缓存的时候删除了），再查询缓存B
        result = this.redisTemplate.opsForList().range(Constants.JHS_KEY_B, start, end);
      }
      log.info("查询结果：{}", result);
    } catch (Exception ex) {
      //这里的异常，一般是redis瘫痪 ，或 redis网络timeout
      log.error("exception:", ex);
      //TODO 走DB查询
    }
    return result;
  }
}
