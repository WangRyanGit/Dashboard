package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.AdPush;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2017/3/20.
 */
public class AdPushDao extends BaseDao {

    private static final String TBLPREFIX = "adpush";
    private static final String CACHE_KEY_ALL = "AdPushDao_all_";
    private static final String CACHE_KEY_ID = "AdPushDao_id_";
    private static final String CACHE_KEY_PKG = "AdPushDao_pkg_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final AdPush item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`message`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMessage() != null) {
                    ps.setString(i++, item.getMessage());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getStatus() != null) {
                    ps.setInt(i++, item.getStatus());
                } else {
                    ps.setInt(i++, 0);
                }

                return ps;
            }
        };
        int id = getJdbcTemplate().update(psc);
        if (id > 0) {
            CacheFactory.delete(CACHE_KEY_ALL);
            CacheFactory.delete(CACHE_KEY_ID + item.getId());
            CacheFactory.delete(CACHE_KEY_PKG + item.getPkg());
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final AdPush item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `message`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getMessage() != null) {
                    ps.setString(i++, item.getMessage());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getStatus() != null) {
                    ps.setInt(i++, item.getStatus());
                } else {
                    ps.setInt(i++, 0);
                }
                ps.setLong(i++, item.getId());
            }
        };
        int id = getJdbcTemplate().update(sb.toString(), psc);
        if (id > 0) {
            CacheFactory.delete(CACHE_KEY_ALL);
            CacheFactory.delete(CACHE_KEY_ID + item.getId());
            CacheFactory.delete(CACHE_KEY_PKG + item.getPkg());
        }
    }


    @SuppressWarnings("unchecked")
    public AdPush findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof AdPush) {
            return (AdPush) obj;
        } else {
            List<AdPush> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            AdPush.class));
            if (list != null && list.size() > 0) {
                AdPush item = list.get(0);
                if (item != null) {
                    CacheFactory.add(CACHE_KEY_ID + id, item,
                            CacheFactory.ONE_MONTH);
                }
                return item;
            }
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    public List<AdPush> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<AdPush>) obj;
        } else {
            List<AdPush> list = query("select * from " + table() + " where status = 0 order by id desc",
                    null, null,
                    new CommonRowMapper(AdPush.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<AdPush> findByPkg(String pkg) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG+pkg );
        if (obj != null && obj instanceof List) {
            return (List<AdPush>) obj;
        } else {
            List<AdPush> list = query("select * from " + table() + " where status = 0 and  pkg = '"+ pkg + "' order by id desc",
                    null, null,
                    new CommonRowMapper(AdPush.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG + pkg , list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<AdPush>();
                CacheFactory.add(CACHE_KEY_PKG + pkg, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    /*
   *web 后台特殊使用
    */
    //根据主键删除一条策略数据
    public void deleteById(Long id){
        String sql = "delete from " + table() + " where 1 = 1 and id = "+id;
        super.getJdbcTemplate().update(sql);
    }
    //查找单个
    public AdPush findById(Long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof AdPush) {
            return (AdPush) obj;
        } else {
        AdPush list = queryForObject("select * from " + table() + " where 1 = 1 and  id = "+ id ,
                AdPush.class);
            if (list != null) {
                CacheFactory.add(CACHE_KEY_ID + id, list,
                        CacheFactory.ONE_MONTH);
            }
        return list;
        }
    }

    //总记录数
    public int findCount() {
        String sql = "select count(1) from " + table();
        //queryForObject()方法中，如果需要返回的是int类型，就写Integer.class,需要返回long类型就写long.class.
        int count = getJdbcTemplate().queryForObject(sql,Integer.class);
        return count;
    }

    //总pkg记录数
    public int findPkgCount(String pkg) {
        String sql = "select count(1) from " + table() + " where 1 = 1 and pkg like '%"+ pkg + "%'";
        //queryForObject()方法中，如果需要返回的是int类型，就写Integer.class,需要返回long类型就写long.class.
        int count = getJdbcTemplate().queryForObject(sql,Integer.class);
        return count;
    }

    /**
     * 分页操作，调用AdResourcesByPage limit分页方法
     * @return
     */
    //具体到某个类型
    public List<AdPush> findByPage(Integer start, Integer size) {
        List<AdPush> list = query("select * from " + table() + " where 1 = 1 LIMIT "+start+","+size,
                null, null,
                new CommonRowMapper(AdPush.class));
        //CacheFactory.delete(CACHE_KEY_ALL);
        return list;
    }

    //具体到pkg
    public List<AdPush> findByPkgPage(String pkg,Integer start, Integer size) {
        List<AdPush> list = query("select * from " + table() + " where 1 = 1 and pkg like '%"+ pkg + "%' order by status desc LIMIT "+start+","+size,
                null, null,
                new CommonRowMapper(AdPush.class));
        CacheFactory.delete(CACHE_KEY_ALL);
        return list;
    }



}
