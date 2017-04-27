package com.web.dao;

import com.factory.CacheFactory;
import com.ibb.dao.BaseDao;
import com.ibb.dao.CommonRowMapper;
import com.mysql.jdbc.Statement;
import com.web.pojo.AdminUser;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */
public class AdminUserDao extends BaseDao {
    private static final String TBLPREFIX = "adminuser";
    private static final String CACHE_KEY_ALL = "AdminUserDao_all_";
    private static final String CACHE_KEY_ID = "AdminUserDao_id_";
    private static final String CACHE_KEY_NAME = "AdminUserDao_name_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final AdminUser item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`username`,`password`, `role`, " +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                if (item.getRole() != null) {
                    ps.setString(i++, item.getRole());
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
    public void update(final AdminUser item) {
        if (item == null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `username`=?,`password`=?, `role`=?, " +
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
                if (item.getRole() != null) {
                    ps.setString(i++, item.getRole());
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


    @SuppressWarnings("unchecked")
    public AdminUser findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof AdminUser) {
            return (AdminUser) obj;
        } else {
            List<AdminUser> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            AdminUser.class));
            if (list != null && list.size() > 0) {
                AdminUser item = list.get(0);
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
    public List<AdminUser> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<AdminUser>) obj;
        } else {
            List<AdminUser> list = query("select * from " + table() + " where status = 0 order by id desc",
                    null, null,
                    new CommonRowMapper(AdminUser.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<AdminUser> findByName(String username) {
        /*Object obj = CacheFactory.get(CACHE_KEY_NAME + username);
        if (obj != null && obj instanceof List) {
            return (List<AdminUser>) obj;
        } else {*/
            List<AdminUser> list = query("select * from " + table() + " where status = 0 and username = '"+ username + "'",
                    null, null,
                    new CommonRowMapper(AdminUser.class));
            /*if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_NAME + username, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<AdminUser>();
                CacheFactory.add(CACHE_KEY_NAME + username, list,
                        CacheFactory.ONE_MONTH);
            }*/
            return list;
        //}
    }

}
