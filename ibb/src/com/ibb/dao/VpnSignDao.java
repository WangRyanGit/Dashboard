package com.ibb.dao;

import com.factory.CacheFactory;
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
 * Created by Ryan on 2017/3/31.
 */
public class VpnSignDao extends BaseDao {
    private static final String TBLPREFIX = "vpn_sign";
    private static final String CACHE_KEY_ALL = "VpnSignDao_all_";
    private static final String CACHE_KEY_ID = "VpnSignDao_id_";
    private static final String CACHE_KEY_PKG_USERID = "VpnSignDao_pkg_userid_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final VpnSign item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`userId`,`begintime`,`type`,`endtime`," +
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
                if (item.getUserId() != null) {
                    ps.setLong(i++, item.getUserId());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getBegintime() != null) {
                    ps.setString(i++, item.getBegintime());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getType() != null) {
                    ps.setString(i++, item.getType());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEndtime() != null) {
                    ps.setString(i++, item.getEndtime());
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
    public void update(final VpnSign item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `pkg`=?,`userId`=?,`begintime`=?,`type`=?,`endtime`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUserId() != null) {
                    ps.setLong(i++, item.getUserId());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getBegintime() != null) {
                    ps.setString(i++, item.getBegintime());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getType() != null) {
                    ps.setString(i++, item.getType());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEndtime() != null) {
                    ps.setString(i++, item.getEndtime());
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
     * 更新用户的签到到期时间
     */
    public int updateByPkgUserid(String begintime,String type,String endtime,String pkg,Long userid) {
        String sql = "UPDATE "+ table() + " SET begintime='" + begintime + "',type='" + type + "',endtime='" + endtime + "', `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 AND userId = '"+ userid+"'AND pkg = '"+ pkg+"'" ;
        int id = getJdbcTemplate().update(sql);
        if (id > 0){
            CacheFactory.delete(CACHE_KEY_PKG_USERID + pkg+ "_" + userid);
        }
        return id;
    }

    /**
     * 是否免费
     */
    public int updatePassByPkgName(String password,String username,String pkg) {
        String sql = "UPDATE "+ table() + " SET password = '" + password + "', `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 AND username = '"+ username+"'AND pkg = '"+ pkg+"'" ;
        return getJdbcTemplate().update(sql);
    }


    @SuppressWarnings("unchecked")
    public VpnSign findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof VpnSign) {
            return (VpnSign) obj;
        } else {
            List<VpnSign> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            VpnSign.class));
            if (list != null && list.size() > 0) {
                VpnSign item = list.get(0);
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
    public List<VpnSign> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<VpnSign>) obj;
        } else {
            List<VpnSign> list = query("select * from " + table() + " where status = 0 order by free desc,country desc",
                    null, null,
                    new CommonRowMapper(VpnSign.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<VpnSign> findByPkgAndUserid(String pkg,Long userId) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG_USERID + pkg+ "_" + userId);
        if (obj != null && obj instanceof List) {
            return (List<VpnSign>) obj;
        } else {
            List<VpnSign> list = query("select * from " + table() + " where status = 0 and userId = '"+ userId + "' and pkg = '"+ pkg + "'order by id desc",
                    null, null,
                    new CommonRowMapper(VpnSign.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG_USERID + pkg+ "_" + userId, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<VpnSign>();
                CacheFactory.add(CACHE_KEY_PKG_USERID + pkg+ "_" + userId, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<VpnSign> findById(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof List) {
            return (List<VpnSign>) obj;
        } else {
            List<VpnSign> list = query("select * from " + table() + " where status = 0 and id = '" + id + "'",
                    null, null,
                    new CommonRowMapper(VpnSign.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ID + id, list,
                        CacheFactory.ONE_MONTH);
            } else {
                list = new ArrayList<VpnSign>();
                CacheFactory.add(CACHE_KEY_ID + id, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }




}
