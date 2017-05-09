package com.ibb.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
			MainSend send=new MainSend();
			ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			AdTokensDao adtokensdao = (AdTokensDao) ctx.getBean("dAdTokensDao");
			String pkg = "com.YouTubePro";
			List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
			System.out.println("tokenlist.size  "+tokenlist.size());
			List<String> tokens= new ArrayList<String>();
			//tokens.add("1b493269a3658ab422697acd791ef96ec730829d3ba8ad46f5479e42d8977cc6");
			for(AdTokens ad : tokenlist) {
				if(ad.getPkg().equals(pkg)){
					System.out.println("token  "+ad.getTokens());
					tokens.add(ad.getTokens().toString());
				}
			}
			String path = "E:\\TubePro_distribution.p12";   //本地测试用
			String password="";
			String message="{'aps':{'alert':'Music without borders, you and I share'}}";
			Integer count=1;
			boolean sendCount=false;
			send.sendpush(tokens, path, password, message, count, sendCount,pkg);




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
