package org.rainbow.kill.redis.limit;

import cn.hutool.script.ScriptUtil;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RScript.Mode;
import org.redisson.api.RScript.ReturnType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author K
 */
@Slf4j
@Component
public class RedisLimit {

  @Resource
  private RedissonClient redissonClient;

  private static final int FAIL_CODE = 0;

  private static Integer limit = 5;

  public Boolean limit() {
    try {
      RScript rScript = redissonClient.getScript();
      String script = getScript("limit.lua");
      // 请求限流
      String key = String.valueOf(System.currentTimeMillis() / 1000);
      // 计数限流
      Object result = rScript.eval(Mode.READ_WRITE, script, ReturnType.VALUE, Collections.singletonList(key), limit);
      if (FAIL_CODE != (Long) result) {
        log.info("成功获取令牌");
        return true;
      }
    } catch (Exception e) {
      log.error("limit 失败：", e);
    }
    return false;
  }

  public static String getScript(String path) {
    StringBuilder stringBuilder = new StringBuilder();

    InputStream inputStream = RedisLimit.class.getClassLoader().getResourceAsStream(path);

    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      String str;
      while ((str = bufferedReader.readLine()) != null) {
        stringBuilder.append(str).append(System.lineSeparator());
      }
    } catch (IOException e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
    }

    return stringBuilder.toString();
  }

}
