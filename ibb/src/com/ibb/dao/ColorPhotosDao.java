package com.ibb.dao;

import com.factory.CacheFactory;
import com.ibb.bean.ColorPhotos;
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
 * Created by Ryan on 2017/4/13.
 */
public class ColorPhotosDao extends BaseDao {
    private static final String TBLPREFIX = "color_photos";
    private static final String CACHE_KEY_ALL = "ColorPhotosDao_all_";
    private static final String CACHE_KEY_ID = "ColorPhotosDao_id_";
    private static final String CACHE_KEY_TYPE_EXAMPLE = "ColorPhotosDao_type_example_";
    private static final String CACHE_KEY_NAME_PHOTO = "ColorPhotosDao_name_photo_";

    public static String table() {
        return TBLPREFIX;
    }

    /**
     * 插入
     */
    public int insert(final ColorPhotos item) {
        if (item == null) {
            return 0;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb
                .append(" (`example`,`type`,`photoName`,`photoUrl`," +
                        " `status`,`createdate`,`updatedate`) VALUES (?, ?, ?, ?, ?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        final String sql = sb.toString();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                if (item.getExample() != null) {
                    ps.setString(i++, item.getExample());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getType() != null) {
                    ps.setString(i++, item.getType());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPhotoName() != null) {
                    ps.setString(i++, item.getPhotoName());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPhotoUrl() != null) {
                    ps.setString(i++, item.getPhotoUrl());
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
     * 批量插入
     */
    public void insertBatch(List<ColorPhotos> photosList){
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(table());
        sb.append(" (`example`,`type`,`photoName`,`photoUrl`," +
                " `status`,`createdate`,`updatedate`) ");
        sb.append(" VALUES (?,?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)");
        List<Object[]> parameters = new ArrayList<Object[]>();
        for(ColorPhotos item:photosList){
            List<Object> parameter = new ArrayList<Object>();
            parameter.add(item.getExample());
            parameter.add(item.getType());
            parameter.add(item.getPhotoName());
            parameter.add(item.getPhotoUrl());
            parameter.add(item.getStatus());
            parameters.add(parameter.toArray());
        }
        super.getJdbcTemplate().batchUpdate(sb.toString(), parameters);
        CacheFactory.get(CACHE_KEY_ALL);
    }

    /**
     * 更新
     */
    public void update(final ColorPhotos item) {
        if (item == null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(table());
        sb
                .append(" SET `example`=?,`type`=?,`photoName`=?,`photoUrl`=?," +
                        " `status`=?, `updatedate`=CURRENT_TIMESTAMP WHERE `id`=?");
        PreparedStatementSetter psc = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                if (item.getExample() != null) {
                    ps.setString(i++, item.getExample());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getType() != null) {
                    ps.setString(i++, item.getType());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPhotoName() != null) {
                    ps.setString(i++, item.getPhotoName());
                } else {
                    ps.setNull(i++, Types.NULL);
                }
                if (item.getPhotoUrl() != null) {
                    ps.setString(i++, item.getPhotoUrl());
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


    @SuppressWarnings("unchecked")
    public ColorPhotos findSingle(long id) {
        Object obj = CacheFactory.get(CACHE_KEY_ID + id);
        if (obj != null && obj instanceof ColorPhotos) {
            return (ColorPhotos) obj;
        } else {
            List<ColorPhotos> list = query("select * from " + table()
                            + " where id=? LIMIT 1", new Object[]{id},
                    new int[]{Types.BIGINT}, new CommonRowMapper(
                            ColorPhotos.class));
            if (list != null && list.size() > 0) {
                ColorPhotos item = list.get(0);
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
    public List<ColorPhotos> findAll() {
        Object obj = CacheFactory.get(CACHE_KEY_ALL);
        if (obj != null && obj instanceof List) {
            return (List<ColorPhotos>) obj;
        } else {
            List<ColorPhotos> list = query("select * from " + table() + " where status = 0 order by id",
                    null, null,
                    new CommonRowMapper(ColorPhotos.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_ALL, list,
                        CacheFactory.ONE_MONTH);
            }
            return list;
        }
    }

    //具体到某个类型
    public List<ColorPhotos> findTypeAndExample(Integer beginPosition, Integer number) {
        Object obj = CacheFactory.get(CACHE_KEY_TYPE_EXAMPLE+beginPosition + "_" + number);
        if (obj != null && obj instanceof List) {
            return (List<ColorPhotos>) obj;
        } else {
        List<ColorPhotos> list = query("select example,type from " + table() + " where status = 0  GROUP BY example,type LIMIT "+beginPosition+","+number,
                null, null,
                new CommonRowMapper(ColorPhotos.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_TYPE_EXAMPLE + beginPosition + "_" + number, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<ColorPhotos>();
                CacheFactory.add(CACHE_KEY_TYPE_EXAMPLE + beginPosition + "_" + number, list,
                        CacheFactory.ONE_MONTH);
            }
        return list;
        }
    }

    //具体到某张图片
    public List<ColorPhotos> findNameAndPhoto(String type,Integer beginPosition, Integer number) {
        Object obj = CacheFactory.get(CACHE_KEY_NAME_PHOTO+type+beginPosition + "_" + number);
        if (obj != null && obj instanceof List) {
            return (List<ColorPhotos>) obj;
        } else {
        List<ColorPhotos> list = query("select id,example,type,photoName,photoUrl from " + table() + " where status = 0 and type='" + type + "' GROUP BY id,example,type,photoName,photoUrl LIMIT "+beginPosition+","+number,
                null, null,
                new CommonRowMapper(ColorPhotos.class));
            if (list != null && list.size() > 0) {
                CacheFactory.add(CACHE_KEY_NAME_PHOTO +type+ beginPosition + "_" + number, list,
                        CacheFactory.ONE_MONTH);
            }
            else{
                list = new ArrayList<ColorPhotos>();
                CacheFactory.add(CACHE_KEY_NAME_PHOTO+ type + beginPosition + "_" + number, list,
                        CacheFactory.ONE_MONTH);
            }
        return list;
        }
    }

}
