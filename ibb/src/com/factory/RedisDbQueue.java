package com.factory;

import com.alibaba.fastjson.JSON;

public class RedisDbQueue
{
    private static RedisDbQueue instance = null;

    public static RedisDbQueue getInstance()
    {
        if (instance == null)
        {
            instance = new RedisDbQueue();
        }
        return instance;
    }

    public Long push(String key, Object obj)
    {
        return RedisFactory.lpush(key, toJson(obj));
    }

    public String toJson(Object obj)
    {
        return JSON.toJSONString(obj);
    }

}
