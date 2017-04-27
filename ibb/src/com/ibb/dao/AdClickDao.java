package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.AdClick;
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
 * Created by Ryan on 2017/3/10.
 */
public class AdClickDao extends BaseDao {
    private final static String TBLPREFIX = "adclick";

    private static final String CACHE_KEY_ALL = "AdClickDao_all_";
    private static final String CACHE_KEY_ID = "AdClickDao_id_";
    private static final String CACHE_KEY_PKG_COUNTRY = "AdClickDao_pkg_country_";
    private static final String CACHE_KEY_PKG = "AdClickDao_pkg_";

    public static String table(){
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final AdClick item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`country`, `position`, `minClick`,`maxClick`, " +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                if (item.getCountry() != null) {
                    ps.setString(i++, item.getCountry());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPosition() != null) {
                    ps.setInt(i++, item.getPosition());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMinClick() != null) {
                    ps.setString(i++, item.getMinClick());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMaxClick() != null) {
                    ps.setString(i++, item.getMaxClick());
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
            CacheFactory.delete(CACHE_KEY_PKG_COUNTRY + item.getPkg() + "_" + item.getCountry());
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final AdClick item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `pkg`=?, `country`=?, `position`=?, `minClick`=?," +
                        "`maxClick`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getCountry() != null) {
                    ps.setString(i++, item.getCountry());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPosition() != null) {
                    ps.setInt(i++, item.getPosition());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMinClick() != null) {
                    ps.setString(i++, item.getMinClick());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMaxClick() != null) {
                    ps.setString(i++, item.getMaxClick());
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
            CacheFactory.delete(CACHE_KEY_PKG_COUNTRY + item.getPkg() + "_" + item.getCountry());
        }
    }

    @SuppressWarnings("unchecked")
    public AdClick findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof AdClick) {
            return (AdClick) obj;
        } else {
            List<AdClick> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            AdClick.class));
            if (list != null && list.size() > 0) {
                AdClick item = list.get(0);
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
    public List<AdClick> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<AdClick>) obj;
        } else {
            List<AdClick> list = query("select * from " + table() + " where status = 0 order by id desc",
                    null, null,
                    new CommonRowMapper(AdClick.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    @SuppressWarnings("unchecked")
    public List<AdClick> findByPkgAndCountry(String pkg, String country) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG_COUNTRY + pkg + "_" + country);
        if (obj != null && obj instanceof List) {
            return (List<AdClick>) obj;
        } else {
            List<AdClick> list = query("select * from " + table() + " where status = 0 and  pkg = '"+ pkg + "' and (locate('" + country + "', country) > 0 or country = 'ALL')  order by id",
                    null, null,
                    new CommonRowMapper(AdClick.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG_COUNTRY + pkg + "_" + country, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<AdClick>();
                CacheFactory.add(CACHE_KEY_PKG_COUNTRY + pkg + "_" + country, list,
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
    public AdClick findById(Long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof AdClick) {
            return (AdClick) obj;
        } else {
        AdClick list = queryForObject("select * from " + table() + " where 1 = 1 and id = "+ id ,
                AdClick.class);
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
     * 分页操作，调用AdClickByPage limit分页方法
     * @return
     */
    //具体到某个类型
    public List<AdClick> findByPage(Integer start, Integer size) {
        List<AdClick> list = query("select * from " + table() + " where 1 = 1 order by status desc LIMIT "+start+","+size,
                null, null,
                new CommonRowMapper(AdClick.class));
        //CacheFactory.delete(CACHE_KEY_ALL);
        return list;
    }
    //具体到pkg
    public List<AdClick> findByPkgPage(String pkg,Integer start, Integer size) {
        List<AdClick> list = query("select * from " + table() + " where 1 = 1 and pkg like '%"+ pkg + "%' order by status desc LIMIT "+start+","+size,
                null, null,
                new CommonRowMapper(AdClick.class));
        CacheFactory.delete(CACHE_KEY_ALL);
        return list;
    }


}
