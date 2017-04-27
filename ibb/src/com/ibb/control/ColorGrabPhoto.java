package com.ibb.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibb.bean.ColorPhotos;
import com.ibb.dao.ColorPhotosDao;
import com.util.Http;
import com.util.SpringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2017/4/12.
 */
public class ColorGrabPhoto {

    public void GrabPhoto(){
        ColorPhotosDao colorPhotosDao = (ColorPhotosDao) SpringHelper.getBean("dColorPhotosDao");
        List<ColorPhotos> photoList = new ArrayList<>();
        try {
            String str = Http.sendGet("http://api2.pigmentapp.co/v3/books/data.json");
            JSONObject json = JSON.parseObject(str);
            JSONArray booksarray = json.getJSONArray("books");
            System.out.println("booksarray "+booksarray.size());
            List<ColorPhotos> colorList = new ArrayList<>();
            // 获取photoList列表
            if (booksarray.size() > 0) {
                for (int i = 0; i < booksarray.size(); i++) {
                    JSONObject item = booksarray.getJSONObject(i);
                    String example = item.getString("tile");
                    String type = item.getString("id");
                    JSONArray arrpage = item.getJSONArray("pages");
                    for (int m=0; m<arrpage.size();m++){
                        JSONObject obj = arrpage.getJSONObject(m);
                        ColorPhotos color = new ColorPhotos();
                        String photoName = obj.getString("id");
                        String photoUrl = obj.getString("thumb");
                        color.setExample("http://api2.pigmentapp.co/v3"+example);
                        color.setType(type);
                        color.setPhotoName(photoName);
                        color.setPhotoUrl("http://api2.pigmentapp.co/v3"+photoUrl);
                        color.setStatus(0);
                        colorList.add(color);
                    }
                }
                photoList.addAll(colorList);
            }
            /*JSONArray recentarray = json.getJSONArray("recent");
            System.out.println("recentarray  "+recentarray.size());
            // 获取photoList列表
            if (recentarray.size() > 0) {
                for (int i = 0; i < recentarray.size(); i++) {
                    JSONObject item = recentarray.getJSONObject(i);
                    ColorPhotos color = new ColorPhotos();
                    String example = item.getString("tile");
                    String type = item.getString("id");
                    JSONArray arrpage = item.getJSONArray("pages");
                    for (int m=0; m<arrpage.size();m++){
                        JSONObject obj = arrpage.getJSONObject(m);
                        String photoName = obj.getString("id");
                        String photoUrl = obj.getString("thumb");
                        color.setExample("http://api2.pigmentapp.co/v3"+example);
                        color.setType(type);
                        color.setPhotoName(photoName);
                        color.setPhotoUrl("http://api2.pigmentapp.co/v3"+photoUrl);
                        color.setStatus(0);
                        photoList.add(color);
                    }
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("photoList  "+photoList.size());
        colorPhotosDao.insertBatch(photoList);
        System.out.println("Photos insert success");
    }
}
