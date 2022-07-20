package com.w.reggie.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author xin
 * @date 2022-07-20-21:11
 */
@Component
public class RedisClear {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 清理redis中缓存的菜品信息
     */
    public void clear(){
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
    }


}
