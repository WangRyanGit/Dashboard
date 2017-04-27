package com.factory;

import java.util.Map;

public class RedisItem
{
    private Map<Long, Integer> value;
    private Long               time;

    public Map<Long, Integer> getValue()
    {
        return value;
    }

    public void setValue(Map<Long, Integer> value)
    {
        this.value = value;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

}
