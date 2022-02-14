package top.yuko.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis Geo 实现附近商家 Controller
 *
 * @author: K
 * @date: 2022/02/12 10:32
 */
@RestController
public class GeoController {

  public static final String CITY = "city";

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @GetMapping("/geoadd")
  public String geoAdd() {
    Map<String, Point> map = new HashMap<>();
    map.put("天安门", new Point(116.403963, 39.915119));
    map.put("故宫", new Point(116.403414, 39.924091));
    map.put("长城", new Point(116.024067, 40.362639));
    redisTemplate.opsForGeo().add(CITY, map);
    return map.toString();
  }

  @GetMapping("/geopos")
  public Point position(String member) {
    List<Point> positionList = this.redisTemplate.opsForGeo().position(CITY, member);
    return CollUtil.isEmpty(positionList) ? null : positionList.get(0);
  }

  @GetMapping("/geohash")
  public String hash(String member) {
    // geohash 算法生成的 base32 编码值
    List<String> hashList = this.redisTemplate.opsForGeo().hash(CITY, member);
    return CollUtil.isEmpty(hashList) ? null : hashList.get(0);
  }

  @GetMapping("/geodist")
  public Distance distance(String member1, String member2) {
    return this.redisTemplate.opsForGeo().distance(CITY, member1, member2, Metrics.KILOMETERS);
  }

  @GetMapping("/georadius")
  public GeoResults<GeoLocation<String>> radius() {
    Circle circle = new Circle(116.418017, 39.914402, Metrics.KILOMETERS.getMultiplier());
    GeoRadiusCommandArgs args = GeoRadiusCommandArgs.newGeoRadiusArgs().includeCoordinates().sortAscending().limit(50);
    return this.redisTemplate.opsForGeo().radius(CITY, circle, args);
  }

  @GetMapping("/georadiusByMember")
  public GeoResults<GeoLocation<String>> radiusByMember(String member) {
    RedisGeoCommands.GeoRadiusCommandArgs args =
        RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance()
            .includeCoordinates().sortAscending().limit(50);
    Distance distance = new Distance(10, Metrics.KILOMETERS);
    return this.redisTemplate.opsForGeo().radius(CITY, member, distance, args);
  }

}
