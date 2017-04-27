package com.ibb.control.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.apple.ios.protos.nano.DataProto;
import com.factory.SrcFactory;

import com.ibb.bean.*;
import com.ibb.control.ApiServlet;


import com.ibb.dao.*;
import com.ibb.util.DateUtil;
import com.ibb.util.StringUtil;
import com.log.Log;
import com.util.Md5;
import com.util.SpringHelper;

public class ApiServletImpl extends ApiServlet {
    private static final long serialVersionUID = 7591994902635538204L;

    @Override
    public ResponseBean process(RequestBean request) {
        int src = request.getReqType();
        ResponseBean ret = null;
        switch (src) {
            case SrcFactory.SRC_GET_USERREGISTER:
                ret = getUserRegistration(request);
                break;
            case SrcFactory.SRC_GET_ADSHOW:
                ret = getAdShowStrategy(request);
                break;
            case SrcFactory.SRC_GET_DATAST:
                ret = getDataStatistics(request);
                break;
            case SrcFactory.SRC_SEND_PUSH:
                ret = getAPNSPush(request);
                break;
            case SrcFactory.SRC_GET_APPS:
                ret = getApps(request);
                break;
        }
        return ret;
    }

    //接口：http://i.iosfreemusic.com/ibbios/api/10 -- 13

    /**
     * 用户注册接口 by Ryan
     * */
    private ResponseBean getUserRegistration(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getUserRegistration");
        ResponseBean result = new ResponseBean(1);
        DataProto.UserRegistrationRequest req = request.getData(new DataProto.UserRegistrationRequest());
        DataProto.UserRegistrationResponse response = new DataProto.UserRegistrationResponse();

        if (req != null){
            if(!StringUtil.isBlank(req.geo) && !StringUtil.isBlank(req.bundleId)){
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append(req.geo).append("\t");
                sb.append(req.os).append("\t");
                sb.append(req.idfa).append("\t");
                sb.append(req.bundleId).append("\t");
                sb.append(req.bundleDisplayName).append("\t");
                sb.append(req.version).append("\t");
                sb.append(req.build);
                Log.regApp(sb.toString());

                String userid = Md5.md5(String.valueOf(System.currentTimeMillis()));
                response.userId = userid;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("User registration info（geo&&pkg）is null");
                Log.regApp(sb.toString());
            }
        }

        response.timeStamp = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time+ "    End getUserRegistration");
        return result;
    }

    /**
     * 广告下发策略接口 by Ryan
     * */
    private ResponseBean getAdShowStrategy(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getAdShowStrategy");
        ResponseBean result = new ResponseBean(1);
        DataProto.AdShowStrategyRequest req = request.getData(new DataProto.AdShowStrategyRequest());
        DataProto.AdShowStrategyResponse response = new DataProto.AdShowStrategyResponse();

        if (req != null){
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.timeStamp).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.bundleDisplayName).append("\t");
            sb.append(req.build).append("\t");
            sb.append(req.version);
            Log.adShow(sb.toString());

        }

        AdControlDao adControlDao = (AdControlDao) SpringHelper.getBean("dadControlDao");
        AdStrategyDao adStrategyDao = (AdStrategyDao)SpringHelper.getBean("dadStrategyDao");
        AdClickDao adClickDao = (AdClickDao)SpringHelper.getBean("dClickDao");
        AdResourcesDao adResourcesDao = (AdResourcesDao) SpringHelper.getBean("dAdResourcesDao");

        String pkg = null;
        String geo = null;
        try{
            geo = req.geo;
            pkg = req.bundleId;
        }catch (Exception e){

        }

        if(!StringUtil.isBlank(pkg)){
            List<DataProto.AdControl> adControlList = new ArrayList<>();
            List<DataProto.AdStrategy> adStrategyList = new ArrayList<>();
            List<DataProto.AdClick> adClicksList = new ArrayList<>();
            List<DataProto.AdResources> adResourcesList = new ArrayList<>();

            if(!StringUtil.isBlank(geo)){
                List<AdControl> adControls = adControlDao.findByPkgAndContry(pkg,geo);
                List<AdStrategy> adStrategies = adStrategyDao.findByPkgAndCountry(pkg,geo);
                List<AdClick> adClicks = adClickDao.findByPkgAndCountry(pkg,geo);
                List<AdResources> adResources = adResourcesDao.findByPkgAndCountry(pkg,geo);

                for(AdControl item : adControls){
                    DataProto.AdControl control = new DataProto.AdControl();
                    control.positionID = item.getPosition();
                    control.init = item.getInitOn();
                    control.show = item.getShowOn();
                    control.requestIntervalTime = item.getRequestInterval();
                    control.status = item.getStatus();
                    adControlList.add(control);
                }

                for(AdStrategy item : adStrategies){
                    /*if (item.getMinVersion() != 0 && version < item.getMinVersion()){
                        continue;
                    }
                    if (item.getMaxVersion() != 0 && version > item.getMaxVersion()){
                        continue;
                    }*/
                    DataProto.AdStrategy adStrategy = new DataProto.AdStrategy();
                    adStrategy.adType = item.getAdType();
                    adStrategy.adSource = item.getAdSource();
                    adStrategy.adID = item.getAdId();
                    adStrategy.positionID = item.getPosition();
                    adStrategy.priority = item.getPriority();
                    adStrategy.adreward = item.getAdreward();
                    adStrategyList.add(adStrategy);
                }
                for (AdClick item : adClicks){
                    DataProto.AdClick click = new DataProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
                /*
                修改此处千万看清楚
                }*/
                for (AdResources ad : adResources){
                    DataProto.AdResources adResources1 = new DataProto.AdResources();
                    adResources1.soundKey = ad.getSourcekey();  //music版本中 为soundKey   维持上线版本 两个key同时启用  ，为后期维护  其他应用数据源表以后统一只有sourcekey
                    adResources1.jamKey = ad.getTypekey();      //music版本中 为jamKey  其他发行软件中均表示key的类型
                    adResources1.type = ad.getType();   //表示使用key的类型
                    adResourcesList.add(adResources1);
                }
            }

            if(adControlList.size() <= 0){
                List<AdControl> allAdcontrol = adControlDao.findByPkgAndContry(pkg,"ALL");
                for(AdControl item : allAdcontrol){
                    DataProto.AdControl control = new DataProto.AdControl();
                    control.init = item.getInitOn();
                    control.show = item.getShowOn();
                    control.requestIntervalTime = item.getRequestInterval();
                    control.positionID = item.getPosition();
                    control.status = item.getStatus();
                    adControlList.add(control);
                }
            }
            if(adStrategyList.size() <= 0){
                List<AdStrategy> allAdStrategy = adStrategyDao.findByPkgAndCountry(pkg,"ALL");
                for(AdStrategy item : allAdStrategy){
                    /*if(item.getMinVersion() != 0 && version < item.getMinVersion()){
                        continue;
                    }
                    if(item.getMaxVersion() != 0 && version > item.getMaxVersion()){
                        continue;
                    }*/
                    DataProto.AdStrategy adStrategy = new DataProto.AdStrategy();
                    adStrategy.adType = item.getAdType();
                    adStrategy.adSource = item.getAdSource();
                    adStrategy.adID = item.getAdId();
                    adStrategy.priority = item.getPriority();
                    adStrategy.positionID = item.getPosition();
                    adStrategy.adreward = item.getAdreward();
                    adStrategyList.add(adStrategy);
                }
            }
            if(adClicksList.size() <= 0){
                List<AdClick> allAdclick = adClickDao.findByPkgAndCountry(pkg,"ALL");
                for(AdClick item : allAdclick){
                    DataProto.AdClick click = new DataProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
            }
            /*
                修改此处千万看清楚
                }*/
            if(adResourcesList.size() <= 0){
                List<AdResources> allResources = adResourcesDao.findByPkgAndCountry(pkg,"ALL");
                for(AdResources item : allResources){
                    DataProto.AdResources resources = new DataProto.AdResources();
                    resources.soundKey = item.getSourcekey();
                    resources.jamKey = item.getTypekey();
                    resources.type = item.getType();
                    adResourcesList.add(resources);
                }
            }
            response.adControlList = adControlList.toArray(new DataProto.AdControl[0]);
            response.adStrategyList = adStrategyList.toArray(new DataProto.AdStrategy[0]);
            response.adClickList = adClicksList.toArray(new DataProto.AdClick[0]);
            response.adResourcesList = adResourcesList.toArray(new DataProto.AdResources[0]);
        }

        response.serverTime = System.currentTimeMillis();

        if(response == null){
            response = new DataProto.AdShowStrategyResponse();
        }
        result.setData(response);
        System.out.println(time+ "    End getAdShowStrategy");
        return result;
    }

    /**
     * 数据统计接口 by Ryan
     * */
    private ResponseBean getDataStatistics(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getDataStatistics");
        ResponseBean result = new ResponseBean(1);
        DataProto.DataStatisticsRequest req = request.getData(new DataProto.DataStatisticsRequest());
        DataProto.DataStatisticsResponse response = new DataProto.DataStatisticsResponse();

        if(req != null){
            if (!StringUtil.isBlank(req.bundleId ) && !StringUtil.isBlank(req.geo)){
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append(req.bundleId).append("\t");
                sb.append(req.geo).append("\t");
                sb.append(req.idfa).append("\t");
                sb.append(req.bundleDisplayName).append("\t");
                sb.append(req.build).append("\t");
                sb.append(req.version).append("\t");
                sb.append(req.eventNumber).append("\t");
                sb.append(req.timeStamp).append("\t");
                //if (req.eventNumber == 1){}   //通过事件编号统计是哪一个事件
                /*List<DataProto.eventApplicationStartup> applicationStartups = new ArrayList<>();
                for(DataProto.eventApplicationStartup item : req.applicationStartupList){
                    DataProto.eventApplicationStartup list = new DataProto.eventApplicationStartup();
                    list.timeStartup = item.timeStartup;
                    list.geo = item.geo;
                    applicationStartups.add(list);
                }*/
                sb.append("eventApplicationStartup").append("\t");
                for(DataProto.eventApplicationStartup item : req.applicationStartupList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeStartup).append("\t");
                }
                sb.append("eventPageSwitching").append("\t");
                for(DataProto.eventPageSwitching item : req.pageSwitchingList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.pageName).append("\t");
                    sb.append(item.timeSwitching).append("\t");
                }
                sb.append("eventAdRequest").append("\t");
                for(DataProto.eventAdRequest item : req.adRequestList) {
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeRequest).append("\t");
                }
                sb.append("eventAdShow").append("\t");
                for(DataProto.eventAdShow item : req.adShowList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeShow).append("\t");
                }
                sb.append("eventAdClick").append("\t");
                for(DataProto.eventAdClick item : req.adClickList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeClick);
                }
                Log.adData(sb.toString());

                response.success = "OK";
                response.status = true;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("Data(geo&&pkg) is null");
                Log.adData(sb.toString());

                response.success = "NO";
                response.status = false;
            }
        }
        System.out.println(time + "    End getDataStatistics");
        result.setData(response);
        return result;

    }

    /**
     * 服务器端推送接口 by Ryan
     * */
    private ResponseBean getAPNSPush(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getAPNSPush");
        ResponseBean result = new ResponseBean(1);
        DataProto.AdSendPushRequest req = request.getData(new DataProto.AdSendPushRequest());
        DataProto.AdSendPushResponse response = new DataProto.AdSendPushResponse();

        if(req != null){
            String pkg = null;
            String country = null;
            String tokens = null;
            String idfa = null;
            try{
                idfa = req.idfa;
                tokens = req.deviceToken;
                country = req.geo;
                pkg = req.bundleId;
            }catch (Exception e){

            }
            if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(country)){
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append(country).append("\t");
                sb.append(pkg).append("\t");
                sb.append(req.idfa).append("\t");
                sb.append(req.bundleDisplayName).append("\t");
                sb.append(req.build).append("\t");
                sb.append(req.version).append("\t");
                sb.append(tokens);
                Log.sendPush(sb.toString());
                response.success = "OK";
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("Push(geo&&pkg) is null");
                Log.sendPush(sb.toString());
                response.success = "NO";
            }
            if (!StringUtil.isBlank(tokens) && !StringUtil.isBlank(pkg) && !StringUtil.isBlank(idfa)){
                tokens = tokens.trim();
                idfa = idfa.trim();
                try {
                    AdTokensDao adTokens = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
                    List<AdTokens> tokenslist = adTokens.findByPkg(pkg);
                    if(tokenslist.size() > 0){
                        for(AdTokens adk : tokenslist) {
                            if (adk.getPkg().equals(pkg) && !adk.getIdfa().equals(idfa)) {
                                AdTokens adto = new AdTokens();
                                adto.setPkg(pkg);
                                adto.setIdfa(idfa);
                                adto.setTokens(tokens);
                                adto.setStatus(0);
                                int rows = adTokens.insert(adto);
                                if (rows > 0) {
                                    System.out.println(time + " 同pkg新idfa    AdTokens insert  " + rows + "  num");
                                    break;
                                } else {
                                    System.out.println(time + " 无同pkg新idfa");
                                }
                            }
                            if (adk.getIdfa().equals(idfa) && adk.getPkg().equals(pkg)){
                                try {
                                    int num = adTokens.updateByPkgAndIdfa(pkg,idfa);
                                    if(num > 0){
                                        System.out.println(time + " (重复idfa下架)    Adtokens update  " + num + " num");
                                    }
                                }catch (Exception e){

                                }
                                try {
                                    AdTokens adt = new AdTokens();
                                    adt.setPkg(pkg);
                                    adt.setIdfa(idfa);
                                    adt.setTokens(tokens);
                                    adt.setStatus(0);
                                    int num = adTokens.insert(adt);
                                    if (num > 0){
                                        System.out.println(time+ " （重复idfa下架后存入最新tokens）    AdTokens insert  "+ num +"  num");
                                        break;
                                    }else {
                                        System.out.println(time+ " 同pkg无重复idfa");
                                    }
                                }catch (Exception e){

                                }
                            }
                        }
                    }else {
                        AdTokens adto = new AdTokens();
                        adto.setPkg(pkg);
                        adto.setIdfa(idfa);
                        adto.setTokens(tokens);
                        adto.setStatus(0);
                        int rows = adTokens.insert(adto);
                        if (rows > 0){
                            System.out.println(time+ " 不同pkg    AdTokens insert  "+ rows +"  num");
                        }else {
                            System.out.println(time+ " 无不同pkg");
                        }
                    }
                }catch (Exception e){

                }
            }

        }
        response.sendTime = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + "    End getAPNSPush");
        return result;
    }

    /**
     * 回返apps 共享
     * */
    private ResponseBean getApps(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getApps");
        ResponseBean result = new ResponseBean(1);
        DataProto.AppsRequest req = request.getData(new DataProto.AppsRequest());
        DataProto.AppsResponse response = new DataProto.AppsResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.version).append("\t");
            sb.append(req.bundleId);
            Log.vpnserver(sb.toString());
        }
        AppsDao appsDao = (AppsDao) SpringHelper.getBean("dAppsDao");
        String pkg = req.bundleId;
        String version = req.version;
        try {
            if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(version)) {
                int rows = appsDao.updateByPkg(pkg,version);
                if (rows > 0){
                    System.out.println(pkg + " 最新版本为 " + version);
                }
                List<Apps> applist = appsDao.findAll();
                System.out.println("去除music系列前  "+applist.size());
                Iterator iter = applist.iterator();
                while (iter.hasNext()){
                    Apps app = (Apps) iter.next();
                    if (app.getPkg().toLowerCase().contains("music")){
                        iter.remove();
                    }
                }
                System.out.println("去除music系列后  "+applist.size());
                List<DataProto.Apps> AppList = new ArrayList<>();
                for(Apps item : applist){
                    DataProto.Apps ap = new DataProto.Apps();
                    ap.bundleId = item.getPkg();
                    ap.version = item.getVersion();
                    ap.url = item.getUrl();
                    ap.icon = item.getIcon();
                    AppList.add(ap);
                }
                response.appsList = AppList.toArray(new DataProto.Apps[0]);
                System.out.println("在 "+ pkg + " 上共享 success");
                response.status = "OK";
            }else {
                System.out.println("apps error");
                response.status = "data_error";
            }
        }catch (Exception e) {

        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " "+ pkg + "    End getApps");
        return result;
    }

    public static void main(String[] args) {
//	   ApplicationContext a = new ClassPathXmlApplicationContext("applicationContext.xml");
//		String key = CacheKeyHeadManager.adControlByPkg_Country_Head.getHead()+"_com.power.fast.charge_us";
//		ConfigResponse response = new ConfigResponse();
//		 response = CacheManager.getProto(key,response);
//		 System.out.println(response);


    }
}
