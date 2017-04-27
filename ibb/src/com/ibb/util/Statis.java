package com.ibb.util;

import java.util.Date;

import com.factory.RedisFactory;

public class Statis
{
    private static Statis instance         = null;

    private static final String  REDIS_KEY_PREFIX = "overseapay-redis-statis#";

    private static final String  ADD_USER_KEY     = REDIS_KEY_PREFIX
                                                          + "adduser#";
    private static final String  ACTIVE_USER_KEY  = REDIS_KEY_PREFIX
                                                          + "activeuser#";

    // minutes
    private static long          startTime        = 0L;
    private static long          endTime          = 0L;

    public static Statis getInstance()
    {
        if (instance == null)
        {
            instance = new Statis();
        }
        return instance;
    }

    private static void checkTime()
    {
        long now = new Date().getTime();
        if (now > endTime)
        {
            startTime = DateUtil.startTime();
            endTime = DateUtil.endTime();
        }
    }

    public static void addUser(String channel, String appid, String country,
            String clientid, Boolean isnew)
    {
        checkTime();

        String key = startTime + "#" + channel + "#" + appid + "#" + country;

        RedisFactory.sadd(ADD_USER_KEY + key, clientid,
                RedisFactory.ONE_MONTH);
        RedisFactory.sadd(ACTIVE_USER_KEY + key, clientid,
                RedisFactory.ONE_MONTH);
    }
}
