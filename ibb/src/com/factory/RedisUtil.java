package com.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibb.util.DateUtil;

public class RedisUtil
{
    private final static Logger                  logger         = Logger
                                                                        .getLogger(RedisUtil.class);
    /**
     * 1分钟过期
     * */
    public static final int                      REDIS_INTERVAL = 5 * 60 * 1000;
    private static Map<String, RedisItem> getMap         = new HashMap<String, RedisItem>();
    private static Map<String, RedisItem> addMap         = new HashMap<String, RedisItem>();
    private static long                          getEndTime     = DateUtil
                                                                        .endTime();
    private static long                          addEndTime     = DateUtil
                                                                        .endTime();

    public static Map<Long, Integer> getAll(String key)
    {
        long now = System.currentTimeMillis();
        RedisItem item = getMap.get(key);
        if ((item == null) || (now - item.getTime() > REDIS_INTERVAL))
        {
            if (item == null)
            {
                item = new RedisItem();
                item.setValue(new HashMap<Long, Integer>());
                getMap.put(key, item);
            }
            Map<String, String> map = RedisFactory.hgetAll(key);
            if (map != null && map.size() > 0)
            {
                Map<Long, Integer> valueMap = new HashMap<Long, Integer>();
                for (String k : map.keySet())
                {
                    String v = map.get(k);
                    valueMap.put(Long.valueOf(k), Integer.valueOf(v));
                }
                item.setValue(valueMap);
            }
            item.setTime(now);
            
            if (getEndTime < now)
            {
                getEndTime = DateUtil.endTime();
                getMap.clear();
            }
        }

        return item.getValue();
    }

    public static void incr(String key, List<Long> list, int liveTime)
    {
        // logger.info("incrtest init... ...");
        long now = System.currentTimeMillis();
        RedisItem item = addMap.get(key);
        // logger.info("111 init... ...");
        if (item == null)
        {
            item = new RedisItem();
            item.setTime(0L);
            item.setValue(new HashMap<Long, Integer>());
            addMap.put(key, item);
        }
        // logger.info("222 init... ...");
        Map<Long, Integer> map = item.getValue();
        if (map == null)
        {
            map = new HashMap<Long, Integer>();
        }
        // logger.info("333 init... ...");
        for (Long id : list)
        {
            Integer v = map.get(id);
            if (v == null)
            {
                v = 0;
            }
            map.put(id, v + 1);
        }
        // logger.info("item.getTime()==null:"+item.getTime()==null);
        if (item.getTime() == null || now - item.getTime() > REDIS_INTERVAL)
        {
            for (Long k : map.keySet())
            {
                Integer v = map.get(k);
                RedisFactory.hincrBy(key, String.valueOf(k), Long
                        .valueOf(v), liveTime);
                map.put(k, 0);
            }
            item.setTime(now);
            
            if (addEndTime < now)
            {
                addEndTime = DateUtil.endTime();
                for (String k : addMap.keySet())
                {
                    RedisItem v = addMap.get(k);
                    map = v.getValue();
                    if (map != null)
                    {
                        for (Long k2 : map.keySet())
                        {
                            Integer v2 = map.get(k);
                            RedisFactory.hincrBy(key, String.valueOf(k2), Long
                                    .valueOf(v2), liveTime);
                        }
                    }
                }
                addMap.clear();
            }
        }
    }
}
