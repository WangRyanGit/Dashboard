package com.ibb.dao;

import java.util.List;
import java.util.Map;

import com.web.dao.CommonRowMapperNew;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class BaseDao extends JdbcDaoSupport
{
    public BaseDao()
    {
    }

    @SuppressWarnings("unchecked")
    public List query(String sql, Object[] args, int[] argTypes,
            RowMapper mapper)
    {
        return getJdbcTemplate().query(sql, args, argTypes, mapper);
    }

    @SuppressWarnings("unchecked")
    public List query(String sql, RowMapper mapper)
    {
        return getJdbcTemplate().query(sql, mapper);
    }

    public List<Map<String, Object>> query(String sql, Object[] args,
            int[] argTypes)
    {
        return getJdbcTemplate().queryForList(sql, args, argTypes);
    }

    protected <T> List<T> queryForList(String sql, Object[] parameters, Class<T> result) {
        return super.getJdbcTemplate().query(sql, parameters, new CommonRowMapperNew<T>(result));
    }

    protected <T> List<T> queryForList(String sql, Class<T> result) {
        return super.getJdbcTemplate().query(sql, new CommonRowMapperNew<T>(result));
    }

    protected <T> T queryForObject(String sql, Object[] parameters, Class<T> result) {
        List<T> list = getJdbcTemplate().query(sql, parameters, new CommonRowMapperNew<T>(result));
        if(list==null || list.isEmpty()) return null;
        return list.get(0);
    }

    protected <T> T queryForObject(String sql, Class<T> result) {
        List<T> list = getJdbcTemplate().query(sql, new CommonRowMapperNew<T>(result));
        if(list==null || list.isEmpty()) return null;
        return list.get(0);
    }

    //spring 3.2.2 之后已经被取消了
    /*public int queryForInt(String sql, Object[] args, int[] argTypes)
    {
        return getJdbcTemplate().queryForInt(sql, args, argTypes);
    }

    public long queryForLong(String sql, Object[] args, int[] argTypes)
    {
        return getJdbcTemplate().queryForLong(sql, args, argTypes);
    }*/

}
