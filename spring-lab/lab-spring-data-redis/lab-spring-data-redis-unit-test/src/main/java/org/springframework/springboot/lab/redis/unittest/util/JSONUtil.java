package org.springframework.springboot.lab.redis.unittest.util;

import com.alibaba.fastjson.JSON;

/**
 * JSON 工具类
 *
 * @author K
 */
public class JSONUtil {

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static String toJSONString(Object javaObject) {
        return JSON.toJSONString(javaObject);
    }

    public static byte[] toJSONBytes(Object javaObject) {
        return JSON.toJSONBytes(javaObject);
    }
}
