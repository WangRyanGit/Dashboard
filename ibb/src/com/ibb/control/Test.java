package com.ibb.control;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helper.RequestHelper;
import com.ibb.bean.AdClick;
import com.ibb.bean.AdStrategy;
import com.ibb.bean.Apps;
import com.ibb.bean.ColorPhotos;
import com.ibb.dao.AdClickDao;
import com.ibb.dao.AdStrategyDao;
import com.ibb.dao.AppsDao;
import com.ibb.dao.ColorPhotosDao;
import com.logic.LBSManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Servlet implementation class Test
 */

public class Test {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		AdClickDao adClickDao = (AdClickDao) ctx.getBean("dClickDao");


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








       
    /**
     * @see HttpServlet#HttpServlet()
     */
    /*public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("curl 请求");
		String ip = RequestHelper.getIpAddr(request);
		String country =LBSManager.getCountryByIp(ip);
		response.getWriter().append("Served at: country //"+country);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	public static int i=0;
	public static void main(String[] args) {
		System.out.println(test());
	}
	public static int test(){
		try {
		return i = i+10;
			
		} finally {
			return 2;
		}
	}*/
}
