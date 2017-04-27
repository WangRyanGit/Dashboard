package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.Apps;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AppsDao extends BaseDao {
    private static final String TBLPREFIX = "apps";
    private static final String CACHE_KEY_ALL = "AppsDao_all_";
    private static final String CACHE_KEY_ID = "AppsDao_id_";
    private static final String CACHE_KEY_PKG = "AppsDao_pkg_";


    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final Apps item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`version`,`url`, `icon`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                if (item.getVersion() != null) {
                    ps.setString(i++, item.getVersion());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUrl() != null) {
                    ps.setString(i++, item.getUrl());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIcon() != null) {
                    ps.setString(i++, item.getIcon());
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
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final Apps item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `pkg`=?,`version`=?, `url`=?, `icon`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getVersion() != null) {
                    ps.setString(i++, item.getVersion());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUrl() != null) {
                    ps.setString(i++, item.getUrl());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIcon() != null) {
                    ps.setString(i++, item.getIcon());
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
        }
    }

    /**
     * 更新version
     */
    public int updateByPkg(String pkg,String version) {
        String sql = "UPDATE "+ table() + " SET `version`= '"+version+"', `updatedate`=CURRENT_TIMESTAMP WHERE `pkg`= '"+pkg+"' AND `status`= 0 ";
        int rows = getJdbcTemplate().update(sql);
        if (rows > 0){
            CacheFactory.delete(CACHE_KEY_ALL);
            CacheFactory.delete(CACHE_KEY_PKG + pkg);
        }
        return rows;
    }


    @SuppressWarnings("unchecked")
    public Apps findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof Apps) {
            return (Apps) obj;
        } else {
            List<Apps> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            Apps.class));
            if (list != null && list.size() > 0) {
                Apps item = list.get(0);
                if (item != null) {
                    CacheFactory.add(CACHE_KEY_ID + id, item,
                            CacheFactory.ONE_MONTH);
                }
                return item;
            }
            return null;
        }
    }

    public Apps findSingle(String pkg) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG + pkg);
        if (obj != null && obj instanceof Apps) {
            return (Apps) obj;
        } else {
            List<Apps> list = query("select * from " + table()
                            + " where pkg=? and status = 0 order by id desc LIMIT 1", new Object[]{pkg},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            Apps.class));
            if (list != null && list.size() > 0) {
                Apps item = list.get(0);
                if (item != null) {
                    CacheFactory.add(CACHE_KEY_PKG + pkg, item,
                            CacheFactory.ONE_MONTH);
                }
                return item;
            }
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Apps> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<Apps>) obj;
        } else {
            List<Apps> list = query("select * from " + table() + " where status = 0 order by id",
                    null, null,
                    new CommonRowMapper(Apps.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<Apps> findByPkg(String pkg) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG+pkg);
        if (obj != null && obj instanceof List) {
            return (List<Apps>) obj;
        } else {
            List<Apps> list = query("select * from " + table() + " where status = 0 and  pkg = '"+ pkg + "' order by id",
                    null, null,
                    new CommonRowMapper(Apps.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG + pkg, list,
                        CacheFactory.ONE_MONTH);
            }else{
                list = new ArrayList<Apps>();
                CacheFactory.add(CACHE_KEY_PKG + pkg, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }
}
