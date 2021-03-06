package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.FollowPurchase;
import com.ibb.util.DateUtil;
import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2017/4/26.
 */
public class FollowPurchaseDao extends BaseDao {

    private static final String TBLPREFIX = "follow_purchase";
    private static final String CACHE_KEY_ALL = "FollowPurchaseDao_all_";
    private static final String CACHE_KEY_ID = "FollowPurchaseDao_id_";
    private static final String CACHE_KEY_RECEIPT = "FollowPurchaseDao_receipt_";
    private static final String CACHE_KEY_PKG_USERID = "FollowPurchaseDao_pkg_userid_";

    public static String table() {
        return TBLPREFIX;
    }
    /**
     * 插入
     */
    public int insert(final FollowPurchase item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`pkg`,`user_id`,`quantity`,`product_id`,`purchase_date`,`expires_date`,`md5_receipt`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?, ?,?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
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
                if (item.getUser_id() != null) {
                    ps.setString(i++, item.getUser_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getQuantity() != null) {
                    ps.setString(i++, item.getQuantity());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getProduct_id() != null) {
                    ps.setString(i++, item.getProduct_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPurchase_date() != null) {
                    ps.setString(i++, item.getPurchase_date());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getExpires_date() != null) {
                    ps.setString(i++, item.getExpires_date());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMd5_receipt() != null) {
                    ps.setString(i++, item.getMd5_receipt());
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
            CacheFactory.delete(CACHE_KEY_RECEIPT + item.getMd5_receipt());
        }
        return id;
    }

    /**
     * 更新
     */
    public void update(final FollowPurchase item) {
        if (item == null) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `pkg`=?,`user_id`=?,`quantity`=?,`product_id`=?,`purchase_date`=?,`expires_date`=?,`md5_receipt`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getPkg() != null) {
                    ps.setString(i++, item.getPkg());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getUser_id() != null) {
                    ps.setString(i++, item.getUser_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getQuantity() != null) {
                    ps.setString(i++, item.getQuantity());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getProduct_id() != null) {
                    ps.setString(i++, item.getProduct_id());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPurchase_date() != null) {
                    try {
                        ps.setString(i++, DateUtil.dateToStamp(item.getPurchase_date()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getExpires_date() != null) {
                    try {
                        ps.setString(i++, DateUtil.dateToStamp(item.getExpires_date()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getMd5_receipt() != null) {
                    ps.setString(i++, item.getMd5_receipt());
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
            CacheFactory.delete(CACHE_KEY_ID + item.getUser_id());
            CacheFactory.delete(CACHE_KEY_PKG_USERID + item.getPkg()+ "_" + item.getUser_id());
        }
    }
    /**
     * 下架follow_purchase表中过期数据
     */
    public int updateByUseridAndPkg(String pkg,String userid) {
        String sql = "UPDATE "+ table() + " SET `status`= -1 , `updatedate`=CURRENT_TIMESTAMP WHERE `status`= 0 AND pkg = '"+ pkg+"' AND user_id = '"+ userid + "'";
        int rows = getJdbcTemplate().update(sql);
        if (rows > 0){
            CacheFactory.delete(CACHE_KEY_ID + userid);
            CacheFactory.delete(CACHE_KEY_PKG_USERID + pkg+ "_" + userid);
        }
        return rows;
    }

    @SuppressWarnings("unchecked")
    public List<FollowPurchase> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<FollowPurchase>) obj;
        } else {
            List<FollowPurchase> list = query("select * from " + table() + " where status = 0",
                    null, null,
                    new CommonRowMapper(FollowPurchase.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    public List<FollowPurchase> findByPkgAndUserid(String pkg, String userId) {
        Object obj = CacheFactory.get(CACHE_KEY_PKG_USERID + pkg+ "_" + userId);
        if (obj != null && obj instanceof List) {
            return (List<FollowPurchase>) obj;
        } else {
            List<FollowPurchase> list = query("select * from " + table() + " where status = 0 and user_id = '"+ userId + "' and pkg = '"+ pkg + "'order by id desc",
                    null, null,
                    new CommonRowMapper(FollowPurchase.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_PKG_USERID + pkg+ "_" + userId, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<FollowPurchase>();
                CacheFactory.add(CACHE_KEY_PKG_USERID + pkg+ "_" + userId, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }


    public List<FollowPurchase> findByMd5Receipt(String md5_receipt) {
        Object obj = CacheFactory.get(CACHE_KEY_RECEIPT + md5_receipt);
        if (obj != null && obj instanceof List) {
            return (List<FollowPurchase>) obj;
        } else {
            List<FollowPurchase> list = query("select * from " + table() + " where status = 0 and md5_receipt = '" + md5_receipt + "'",
                    null, null,
                    new CommonRowMapper(FollowPurchase.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_RECEIPT + md5_receipt, list,
                        CacheFactory.ONE_MONTH);
            } else {
                list = new ArrayList<FollowPurchase>();
                CacheFactory.add(CACHE_KEY_RECEIPT + md5_receipt, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    /*
    *web 后台特殊使用
     */
    //查找单个
    public FollowPurchase findById(Long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof FollowPurchase) {
            return (FollowPurchase) obj;
        } else {
            FollowPurchase list = queryForObject("select id,pkg,user_id,FROM_UNIXTIME(purchase_date / 1000,'%Y-%m-%d %H:%i:%s') as purchase_date,FROM_UNIXTIME(expires_date / 1000,'%Y-%m-%d %H:%i:%s') as expires_date,`status`,quantity,product_id,md5_receipt,createdate,updatedate from " + table() + " where 1 = 1 and  id = " + id, FollowPurchase.class);
            if (list != null) {
                CacheFactory.add(CACHE_KEY_ID + id, list, CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }
    //总记录数
    public int findCount(String pkg,String user_id) {
        String sql = "select count(1) from " + table() + " where 1 = 1 and pkg like '%"+ pkg + "%'and user_id like '%"+ user_id + "%'";
        //queryForObject()方法中，如果需要返回的是int类型，就写Integer.class,需要返回long类型就写long.class.
        int count = getJdbcTemplate().queryForObject(sql,Integer.class);
        return count;
    }
    //分页
    public List<FollowPurchase> findData(String pkg, String user_id, Integer start, Integer size) {
        List<FollowPurchase> list = query("SELECT id,pkg,user_id,FROM_UNIXTIME(purchase_date / 1000,'%Y-%m-%d %H:%i:%s') as purchase_date,FROM_UNIXTIME(expires_date / 1000,'%Y-%m-%d %H:%i:%s') as expires_date,`status`,quantity,product_id,md5_receipt,createdate,updatedate FROM "+ table()
                        + " where 1 = 1 and pkg like '%"+ pkg + "%'and user_id like '%" + user_id + "%' LIMIT "+start+","+size,
                null, null,
                new CommonRowMapper(FollowPurchase.class));
        CacheFactory.delete(CACHE_KEY_ALL);
        return list;
    }


}
