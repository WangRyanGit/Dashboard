package com.ibb.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helper.RequestHelper;
import com.ibb.bean.*;
import com.ibb.dao.*;
import com.ibb.util.DateUtil;
import com.logic.LBSManager;
import com.util.SpringHelper;
import com.web.pojo.VpnData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Servlet implementation class Test
 */

public class Test {
	public static void main(String[] args){

		int a = 10;
		int b = 20;
		System.out.println("a:"+a+",b:"+b); //输出结果 a:10,b:20
		change(a,b);
		System.out.println("a:"+a+",b:"+b); //输出结果 a:10,b:20   基本类型的参数传递


		int[] arr = {1,2,3,4,5};
		change(arr);
		System.out.println(arr[1]); //输出结果 4   引用类型的参数传递
	}


	public static void change(int a,int b) { //a=10,b=20
		System.out.println("1111111a:"+a+",b:"+b); //a:10,b:20
		a = b;  //a=20
		b = a + b; //b=40
		System.out.println("222222a:"+a+",b:"+b); //a:20,b:40
	}


	public static void change(int[] arr) {   //arr={1,2,3,4,5};
		for(int x=0; x<arr.length; x++) {
			if(arr[x]%2==0) {
				arr[x]*=2;
			}
		}
		//arr={1,4,3,8,5};

		/*ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		AdStrategyDao adStrategyDao = (AdStrategyDao) ctx.getBean("dadStrategyDao");
		int count = adStrategyDao.findCount();
		System.out.println(count);
		adStrategyDao.deleteById(309l);
		AdStrategy ads = adStrategyDao.findById(309l);
		System.out.println(ads.getAdId());
		List<AdStrategy> list = adStrategyDao.findByPage(0,10);
		System.out.println(list.size());*/

		/*AppsDao appsDao = (AppsDao) ctx.getBean("dAppsDao");
		List<Apps> applelist = appsDao.findAll();
		System.out.println("fast  "+applelist.size());
		Iterator iter = applelist.iterator();
		while (iter.hasNext()){
			Apps app = (Apps) iter.next();
			if (app.getPkg().toLowerCase().contains("music")){
				iter.remove();
			}
		}
		for(Apps ap : applelist){
			System.out.println(ap.getPkg());
		}
		System.out.println("second  "+applelist.size());*/

	}

}
