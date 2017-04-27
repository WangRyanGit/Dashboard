package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.VpnUsers;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.*;
import java.util.*;

/**
 * Created by Ryan on 2017/3/28.
 */
public class VpnUsersDao extends BaseDao {
    private static final String TBLPREFIX = "vpn_users";
    private static final String CACHE_KEY_ALL = "VpnUsersDao_all_";
    private static final String CACHE_KEY_ID = "VpnUsersDao_id_";
    private static final String CACHE_KEY_PKG_NAME = "VpnUsersDao_pkg_name_";
    private static final String CACHE_KEY_PKG_NAME_PASS = "VpnUsersDao_pkg_name_pass_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final VpnUsers item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`username`,`password`,`pkg`,`regtime`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getUsername() != null) {
                    ps.setString(i++, item.getUsername());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPassword() != null) {
                    ps.setString(i++, item.getPassword());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getRegtime() != null) {
                    ps.setString(i++, item.getRegtime());
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
            CacheFactory.delete(CACHE_KEY_PKG_NAME + item.getPkg()+ "_" + item.getUsername());
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final VpnUsers item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `username`=?,`password`=?,`pkg`=?,`regtime`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getUsername() != null) {
                    ps.setString(i++, item.getUsername());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPassword() != null) {
                    ps.setString(i++, item.getPassword());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getRegtime() != null) {
                    ps.setString(i++, item.getRegtime());
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
     * 更新用户的付费时间
     */
    public int updateRegtime(String regtime,Long id) {
        String sql = "UPDATE "+ table() + " SET regtime = '" + regtime + "', `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 AND id = "+ id;
        int rows = getJdbcTemplate().update(sql);
        if (rows > 0){
            CacheFactory.delete(CACHE_KEY_ID + id);
        }
        return rows;
    }

    /**
     * 是否免费
     */
    public int updatePassByPkgName(String newpass,String username,String password,String pkg) {
        String sql = "UPDATE "+ table() + " SET password = '" + newpass + "', `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 AND username = '"+ username+"'AND password = '"+ password+"'AND pkg = '"+ pkg+"'" ;
        int rows = getJdbcTemplate().update(sql);
        if (rows > 0){
            CacheFactory.delete(CACHE_KEY_PKG_NAME + pkg+ "_" + username);
            CacheFactory.delete(CACHE_KEY_PKG_NAME_PASS + pkg+ "_" + username+"_"+password);
        }
        return rows;
    }


    @SuppressWarnings("unchecked")
    public VpnUsers findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof VpnUsers) {
            return (VpnUsers) obj;
        } else {
            List<VpnUsers> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            VpnUsers.class));
            if (list != null && list.size() > 0) {
                VpnUsers item = list.get(0);
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
    public List<VpnUsers> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<VpnUsers>) obj;
        } else {
        List<VpnUsers> list = query("select * from " + table() + " where status = 0 order by free desc,country desc",
                null, null,
                new CommonRowMapper(VpnUsers.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
        return list;
        }
    }

    public List<VpnUsers> findByNameAndPassAndPkg(String username,String password,String pkg) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG_NAME_PASS + pkg+ "_" + username+"_"+password);
        if (obj != null && obj instanceof List) {
            return (List<VpnUsers>) obj;
        } else {
        List<VpnUsers> list = query("select * from " + table() + " where status = 0 and username = '"+ username + "' and password = '"+ password + "' and pkg = '"+ pkg + "'order by id desc",
                null, null,
                new CommonRowMapper(VpnUsers.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG_NAME_PASS + pkg+ "_" + username+"_"+password, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<VpnUsers>();
                CacheFactory.add(CACHE_KEY_PKG_NAME_PASS+ pkg+ "_" + username+"_"+password, list,
                        CacheFactory.ONE_MONTH);
            }
        return list;
       }
    }

    public List<VpnUsers> findNameAndPkg(String username,String pkg) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG_NAME + pkg+ "_" + username);
        if (obj != null && obj instanceof List) {
            return (List<VpnUsers>) obj;
        } else {
        List<VpnUsers> list = query("select * from " + table() + " where status = 0 and username = '"+ username + "' and pkg = '"+ pkg + "' order by id desc",
                null, null,
                new CommonRowMapper(VpnUsers.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG_NAME + pkg+ "_" + username, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<VpnUsers>();
                CacheFactory.add(CACHE_KEY_PKG_NAME + pkg+ "_" + username, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }


    public List<VpnUsers> findById(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof List) {
            return (List<VpnUsers>) obj;
        } else {
            List<VpnUsers> list = query("select * from " + table() + " where status = 0 and id = '" + id + "'",
                    null, null,
                    new CommonRowMapper(VpnUsers.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ID + id, list,
                        CacheFactory.ONE_MONTH);
            } else {
                list = new ArrayList<VpnUsers>();
                CacheFactory.add(CACHE_KEY_ID + id, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
       }
    }

}
