package com.ibb.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibb.bean.ColorPhotos;
import com.ibb.dao.ColorPhotosDao;
import com.util.Http;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.*;

public class Test2{
	/*打印方法*/
	private static void foreach(List list) {
		for (Object object : list) {
			System.out.println(object + " ");
		}
		System.out.println();

	}

	public static void main(String[] args) {
		/*ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		VpnServersDao vpnServersDao = (VpnServersDao) ctx.getBean("dVpnServersDao");
		List<VpnServers> serverlist = vpnServersDao.findAll();
		List<VpnServers> newlist = new ArrayList<>();
		String coun = null;//标记国家
		Integer num = 1; //累加字段(NO用)
		Integer row = 1; //累加字段(YES用)
		for (VpnServers vpn : serverlist) {
			if (vpn.getFree().equals("NO")){
				VpnServers vp = new VpnServers();
				if(vpn.getCountry().equals(coun)){
					vp.setCountry(vpn.getCountry());
					vp.setIp(vpn.getIp());
					vp.setServer_load(String.valueOf(num)); //相同 累加  num++;
					vp.setFree(vpn.getFree());
					vp.setFlagurl(vpn.getFlagurl());
					newlist.add(vp);
				}else {
					num =1;  //不相同 初始化1
					coun = vpn.getCountry();
					vp.setCountry(vpn.getCountry());
					vp.setIp(vpn.getIp());
					vp.setServer_load(String.valueOf(num));
					vp.setFree(vpn.getFree());
					vp.setFlagurl(vpn.getFlagurl());
					newlist.add(vp);
				}
				num++;
			}else {
				VpnServers vp = new VpnServers();
				vp.setCountry(vpn.getCountry());
				vp.setIp(vpn.getIp());
				vp.setServer_load(String.valueOf(row));
				vp.setFree(vpn.getFree());
				vp.setFlagurl(vpn.getFlagurl());
				newlist.add(vp);
			}
			row++;
		}
		for(VpnServers sd : newlist){
			System.out.println(sd.getCountry()+"  "+sd.getIp()+"  "+sd.getServer_load()+ " " + sd.getFree() + "  " +sd.getFlagurl());;
		}*/

		/*ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		ColorPhotosDao colorPhotosDao = (ColorPhotosDao) ctx.getBean("dColorPhotosDao");
		List<ColorPhotos> typelist = colorPhotosDao.findTypeAndExample(0, 10);
		for (ColorPhotos co : typelist){
			System.out.println(co.getType() + "  " + co.getExample());
		}
		List<ColorPhotos> photolist = colorPhotosDao.findNameAndPhoto("clever_birds",0, 10);
		for (ColorPhotos co : photolist){
			System.out.println(co.getPhotoName() + "  " + co.getPhotoUrl());
		}*/


		//ApplicationContext ctx =new ClassPathXmlApplicationContext("applicationContext.xml");
		//ColorPhotosDao colorPhotosDao = (ColorPhotosDao) ctx.getBean("dColorPhotosDao");
		List<ColorPhotos> photoList = new ArrayList<>();
		try {
			String str = Http.sendGet("http://api2.pigmentapp.co/v3/books/data.json");
			JSONObject json = JSON.parseObject(str);
			JSONArray booksarray = json.getJSONArray("books");
			System.out.println("booksarray "+booksarray.size());
			List<ColorPhotos> colorList = new ArrayList<>();
			// 获取photoList列表
			/*if (booksarray.size() > 0) {
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
			}*/
			JSONArray recentarray = json.getJSONArray("recent");
			System.out.println("recentarray  "+recentarray.size());
			// 获取photoList列表
			if (recentarray.size() > 0) {
				for (int i = 0; i < recentarray.size(); i++) {
					JSONObject item = recentarray.getJSONObject(i);
					String example = item.getString("tile");
					String type = item.getString("id");
					JSONArray arrpage = item.getJSONArray("pages");
					for (int m=0; m<arrpage.size();m++){
						ColorPhotos color = new ColorPhotos();
						JSONObject obj = arrpage.getJSONObject(m);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("photoList  "+photoList.size());
		//colorPhotosDao.insertBatch(photoList);
		System.out.println("Photos insert success");
	}
}
