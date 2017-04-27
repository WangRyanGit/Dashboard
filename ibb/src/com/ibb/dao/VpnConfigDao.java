package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.VpnConfig;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Ryan on 2017/3/24.
 */
public class VpnConfigDao extends BaseDao {
    private static final String TBLPREFIX = "vpn_config";
    private static final String CACHE_KEY_ALL = "VpnConfigDao_all_";
    private static final String CACHE_KEY_ID = "VpnConfigDao_id_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final VpnConfig item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`psk`,`eap_user`,`remote_id`,`eap_passwd`,`local_id`,`type`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getPsk() != null) {
                    ps.setString(i++, item.getPsk());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEap_user() != null) {
                    ps.setString(i++, item.getEap_user());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getRemote_id() != null) {
                    ps.setString(i++, item.getRemote_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEap_passwd() != null) {
                    ps.setString(i++, item.getEap_passwd());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getLocal_id() != null) {
                    ps.setString(i++, item.getLocal_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getType() != null) {
                    ps.setString(i++, item.getType());
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
    public void update(final VpnConfig item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `psk`=?,`eap_user`=?,`remote_id`=?,`eap_passwd`=?,`local_id`=?,`type`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getPsk() != null) {
                    ps.setString(i++, item.getPsk());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEap_user() != null) {
                    ps.setString(i++, item.getEap_user());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getRemote_id() != null) {
                    ps.setString(i++, item.getRemote_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getEap_passwd() != null) {
                    ps.setString(i++, item.getEap_passwd());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getLocal_id() != null) {
                    ps.setString(i++, item.getLocal_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getType() != null) {
                    ps.setString(i++, item.getType());
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
        return getJdbcTemplate().update(sql);
    }


    @SuppressWarnings("unchecked")
    public VpnConfig findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof VpnConfig) {
            return (VpnConfig) obj;
        } else {
            List<VpnConfig> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            VpnConfig.class));
            if (list != null && list.size() > 0) {
                VpnConfig item = list.get(0);
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
    public List<VpnConfig> findAll() {
       Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<VpnConfig>) obj;
        } else {
            List<VpnConfig> list = query("select * from " + table() + " where status = 0 order by id desc",
                    null, null,
                    new CommonRowMapper(VpnConfig.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

}
