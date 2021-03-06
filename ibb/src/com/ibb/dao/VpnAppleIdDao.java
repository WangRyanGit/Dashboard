package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.VpnAppleId;
import com.ibb.bean.VpnConfig;
import com.ibb.bean.VpnSign;
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
 * Created by Ryan on 2017/4/6.
 */
public class VpnAppleIdDao extends BaseDao {
    private static final String TBLPREFIX = "vpn_appleid";
    private static final String CACHE_KEY_ALL = "VpnAppleIdDao_all_";
    private static final String CACHE_KEY_ID = "VpnAppleIdDao_id_";
    private static final String CACHE_KEY_PKG = "VpnAppleIdDao_pkg_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final VpnAppleId item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`appleid`," +
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
                if (item.getAppleid() != null) {
                    ps.setString(i++, item.getAppleid());
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
    public void update(final VpnAppleId item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `pkg`=?,`appleid`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getAppleid() != null) {
                    ps.setString(i++, item.getAppleid());
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
     * 更新
     */
    public int updateById(Integer id) {
        String sql = "UPDATE "+ table() + " SET `status`=-1, `updatedate`=CURRENT_TIMESTAMP WHERE `id`= '"+id+"' AND `status`= 0 ";
        int rows  = getJdbcTemplate().update(sql);
        if (rows > 0){
            CacheFactory.delete(CACHE_KEY_ID + id);
        }
        return rows;
    }


    @SuppressWarnings("unchecked")
    public VpnAppleId findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof VpnAppleId) {
            return (VpnAppleId) obj;
        } else {
            List<VpnAppleId> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            VpnAppleId.class));
            if (list != null && list.size() > 0) {
                VpnAppleId item = list.get(0);
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
    public List<VpnAppleId> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<VpnAppleId>) obj;
        } else {
            List<VpnAppleId> list = query("select * from " + table() + " where status = 0 order by id desc",
                    null, null,
                    new CommonRowMapper(VpnAppleId.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<VpnAppleId> findByPkg(String pkg) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG + pkg);
        if (obj != null && obj instanceof List) {
            return (List<VpnAppleId>) obj;
        } else {
            List<VpnAppleId> list = query("select * from " + table() + " where status = 0 and pkg = '"+ pkg + "'order by id desc",
                    null, null,
                    new CommonRowMapper(VpnAppleId.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG + pkg, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<VpnAppleId>();
                CacheFactory.add(CACHE_KEY_PKG + pkg, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

}
