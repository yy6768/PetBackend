package com.example.petbackend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class ExamRedisUtil {

    /**
     * 如果你使用其他类型的 比如RedisTemplate<String,Object>
     * 那么用 @Resource 注解
     * */
    private final RedisTemplate<String,String> redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ExamRedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存value，有设置超时时间
     * @param key 键
     * @param value 值
     * @param time 超时时间
     */
    public boolean cacheValue(String key, String value, long time) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            if (time > 0) {
                // 如果有设置超时时间的话
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "] " + e.getMessage());
        }
        return false;
    }

    /**
     * 缓存value，没有设置超时时间
     * @param key 键
     * @param value 值
     */
    public boolean cacheValue(String key, String value) {
        return cacheValue(key, value, -1);
    }

    /**
     * 判断缓存是否存在
     * @param key 键
     */
    public boolean containsKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Throwable e) {
            logger.error("判断缓存是否存在时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 根据key，获取缓存
     * @param key 键
     */
    public String getValue(String key) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(key);
        } catch (Throwable e) {
            logger.error("获取缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 移除缓存
     * @param key 键
     */
    public boolean removeValue(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable e) {
            logger.error("移除缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 根据前缀移除所有以传入前缀开头的key-value
     * @param pattern 正则表达式
     */
    public boolean removeKeys(String pattern) {
        try {
            Set<String> keySet = redisTemplate.keys(pattern + "*");
            if (keySet != null) {
                redisTemplate.delete(keySet);
            }
            return true;
        } catch (Throwable e) {
            logger.error("移除key[" + pattern + "]前缀的缓存时失败", "err[" + e.getMessage() + "]");
        }
        return false;
    }

}


