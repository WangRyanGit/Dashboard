package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.VpnServers;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Ryan on 2017/3/21.
 */
public class VpnServersDao extends BaseDao {

    private static final String TBLPREFIX = "vpn_servers";
    private static final String CACHE_KEY_ALL = "VpnServersDao_all_";
    private static final String CACHE_KEY_ID = "VpnServersDao_id_";
    private static final String CACHE_KEY_IP_COUNTRY = "VpnServersDao_ip_country_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final VpnServers item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`country`,`server_load`,`ip`,`flagurl`,`free`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?, ?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getCountry() != null) {
                    ps.setString(i++, item.getCountry());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getServer_load() != null) {
                    ps.setString(i++, item.getServer_load());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIp() != null) {
                    ps.setString(i++, item.getIp());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getFlagurl() != null) {
                    ps.setString(i++, item.getFlagurl());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getFree() != null) {
                    ps.setString(i++, item.getFree());
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
    public void update(final VpnServers item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `country`=?,`server_load`=?,`ip`=?,`flagurl`=?,`free`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getCountry() != null) {
                    ps.setString(i++, item.getCountry());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getServer_load() != null) {
                    ps.setString(i++, item.getServer_load());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getIp() != null) {
                    ps.setString(i++, item.getIp());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getFlagurl() != null) {
                    ps.setString(i++, item.getFlagurl());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getFree() != null) {
                    ps.setString(i++, item.getFree());
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
    public int updateAll() {
        String sql = "UPDATE "+ table() + " SET `status`=-1, `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 ";
        return getJdbcTemplate().update(sql);
    }

    /**
     * 是否免费
     */
    public int updateFree() {
        String sql = "UPDATE "+ table() + " SET free = 'YES', `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 AND country = 'US' LIMIT 2";
        return getJdbcTemplate().update(sql);
    }


    @SuppressWarnings("unchecked")
    public VpnServers findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof VpnServers) {
            return (VpnServers) obj;
        } else {
            List<VpnServers> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            VpnServers.class));
            if (list != null && list.size() > 0) {
                VpnServers item = list.get(0);
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
    public List<VpnServers> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<VpnServers>) obj;
        } else {
            List<VpnServers> list = query("select * from " + table() + " where status = 0 order by free desc,country desc",
                    null, null,
                    new CommonRowMapper(VpnServers.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    //爬下来的ip和原有ip遍历
    public List<VpnServers> findByIpAndContry(String ip, String country) {
        /*Object obj = CacheFactory.get(CACHE_KEY_IP_COUNTRY+ip + "_" + country);
        if (obj != null && obj instanceof List) {
            return (List<VpnServers>) obj;
        } else {*/
            List<VpnServers> list = query("select * from " + table() + " where status = 0  and  ip = '"+ ip + "' and  country = '"+ country + "' order by id desc",
                    null, null,
                    new CommonRowMapper(VpnServers.class));
            /*if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_IP_COUNTRY + ip + "_" + country, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<VpnServers>();
                CacheFactory.add(CACHE_KEY_IP_COUNTRY + ip + "_" + country, list,
                        CacheFactory.ONE_MONTH);
            }*/
            return list;
       // }
    }

}
