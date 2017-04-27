package com.ibb.control.impl;

import com.apple.ios.protos.nano.YouTbProto;
import com.factory.SrcFactory;
import com.ibb.bean.*;
import com.ibb.control.YouTbServlet;
import com.ibb.dao.*;
import com.ibb.util.DateUtil;
import com.ibb.util.StringUtil;
import com.log.Log;
import com.util.Md5;
import com.util.SpringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 2017/4/1.
 */
public class YouTbServletImpl extends YouTbServlet {
    private static final long serialVersionUID = 8491891778314384178L;

    @Override
    public ResponseBean process(RequestBean request) {
        int src = request.getReqType();
        ResponseBean ret = null;
        switch (src) {
            case SrcFactory.SRC_YOU_REGISTER:
                ret = getYouTubeRegistration(request);
                break;
            case SrcFactory.SRC_YOU_STRATEGY:
                ret = getYouTubeStrategy(request);
                break;
            case SrcFactory.SRC_YOU_DATA:
                ret = getYouTbData(request);
                break;
            case SrcFactory.SRC_YOU_SENDPUSH:
                ret = getYouTbPush(request);
                break;
        }
        return ret;
    }

    //接口：http://i.iosfreemusic.com/ibbios/youtb/35 -- 38

    /**
     * YouTube 用户注册接口 by Ryan
     * */
    private ResponseBean getYouTubeRegistration(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getYouTubeRegistration");
        ResponseBean result = new ResponseBean(1);
        YouTbProto.YouTbRegistrationRequest req = request.getData(new YouTbProto.YouTbRegistrationRequest());
        YouTbProto.YouTbRegistrationResponse response = new YouTbProto.YouTbRegistrationResponse();

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
                Log.YouTube(sb.toString());

                String userid = Md5.md5(String.valueOf(System.currentTimeMillis()));
                response.userId = userid;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("YouTube "+ req.bundleId +" registration info（geo&&pkg）is null");
                Log.YouTube(sb.toString());
            }
        }

        response.timeStamp = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time+ "    End getYouTubeRegistration");
        return result;
    }

    /**
     *YouTube 广告下发策略接口 by Ryan
     * */
    private ResponseBean getYouTubeStrategy(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getYouTubeStrategy");
        ResponseBean result = new ResponseBean(1);
        YouTbProto.YouTbStrategyRequest req = request.getData(new YouTbProto.YouTbStrategyRequest());
        YouTbProto.YouTbStrategyResponse response = new YouTbProto.YouTbStrategyResponse();

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
            Log.YouTube(sb.toString());

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
            List<YouTbProto.AdControl> adControlList = new ArrayList<>();
            List<YouTbProto.AdStrategy> adStrategyList = new ArrayList<>();
            List<YouTbProto.AdClick> adClicksList = new ArrayList<>();
            List<YouTbProto.AdResources> adResourcesList = new ArrayList<>();

            if(!StringUtil.isBlank(geo)){
                List<AdControl> adControls = adControlDao.findByPkgAndContry(pkg,geo);
                List<AdStrategy> adStrategies = adStrategyDao.findByPkgAndCountry(pkg,geo);
                List<AdClick> adClicks = adClickDao.findByPkgAndCountry(pkg,geo);
                List<AdResources> adResources = adResourcesDao.findByPkgAndCountry(pkg,geo);

                for(AdControl item : adControls){
                    YouTbProto.AdControl control = new YouTbProto.AdControl();
                    control.positionID = item.getPosition();
                    control.init = item.getInitOn();
                    control.show = item.getShowOn();
                    control.requestIntervalTime = item.getRequestInterval();
                    control.status = item.getStatus();
                    adControlList.add(control);
                }
                for(AdStrategy item : adStrategies){
                    YouTbProto.AdStrategy adStrategy = new YouTbProto.AdStrategy();
                    adStrategy.adType = item.getAdType();
                    adStrategy.adSource = item.getAdSource();
                    adStrategy.adID = item.getAdId();
                    adStrategy.positionID = item.getPosition();
                    adStrategy.priority = item.getPriority();
                    adStrategy.adreward = item.getAdreward();
                    adStrategyList.add(adStrategy);
                }
                for (AdClick item : adClicks){
                    YouTbProto.AdClick click = new YouTbProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
                for (AdResources ad : adResources){
                    YouTbProto.AdResources adResources1 = new YouTbProto.AdResources();
                    adResources1.sourceKey = ad.getSourcekey();
                    adResources1.typeKey = ad.getTypekey();
                    adResources1.type = ad.getType();
                    adResourcesList.add(adResources1);
                }
            }

            if(adControlList.size() <= 0){
                List<AdControl> allAdcontrol = adControlDao.findByPkgAndContry(pkg,"ALL");
                for(AdControl item : allAdcontrol){
                    YouTbProto.AdControl control = new YouTbProto.AdControl();
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
                    YouTbProto.AdStrategy adStrategy = new YouTbProto.AdStrategy();
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
                    YouTbProto.AdClick click = new YouTbProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
            }
            if(adResourcesList.size() <= 0){
                List<AdResources> allResources = adResourcesDao.findByPkgAndCountry(pkg,"ALL");
                for(AdResources item : allResources){
                    YouTbProto.AdResources resources = new YouTbProto.AdResources();
                    resources.sourceKey = item.getSourcekey();  //key
                    resources.typeKey = item.getTypekey();  //key的种类
                    resources.type = item.getType();    //使用哪种key
                    adResourcesList.add(resources);
                }
            }
            response.adControlList = adControlList.toArray(new YouTbProto.AdControl[0]);
            response.adStrategyList = adStrategyList.toArray(new YouTbProto.AdStrategy[0]);
            response.adClickList = adClicksList.toArray(new YouTbProto.AdClick[0]);
            response.adResourcesList = adResourcesList.toArray(new YouTbProto.AdResources[0]);
        }

        response.serverTime = System.currentTimeMillis();

        if(response == null){
            response = new YouTbProto.YouTbStrategyResponse();
        }
        result.setData(response);
        System.out.println(time + "    End getYouTubeStrategy");
        return result;
    }

    /**
     *YouTb 数据统计接口 by Ryan
     * */
    private ResponseBean getYouTbData(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getYouTbData");
        ResponseBean result = new ResponseBean(1);
        YouTbProto.YouTbDataRequest req = request.getData(new YouTbProto.YouTbDataRequest());
        YouTbProto.YouTbDataResponse response = new YouTbProto.YouTbDataResponse();

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
                sb.append("eventApplicationStartup").append("\t");
                for(YouTbProto.eventApplicationStartup item : req.applicationStartupList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeStartup).append("\t");
                }
                sb.append("eventPageSwitching").append("\t");
                for(YouTbProto.eventPageSwitching item : req.pageSwitchingList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.pageName).append("\t");
                    sb.append(item.timeSwitching).append("\t");
                }
                sb.append("eventAdRequest").append("\t");
                for(YouTbProto.eventAdRequest item : req.adRequestList) {
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeRequest).append("\t");
                }
                sb.append("eventAdShow").append("\t");
                for(YouTbProto.eventAdShow item : req.adShowList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeShow).append("\t");
                }
                sb.append("eventAdClick").append("\t");
                for(YouTbProto.eventAdClick item : req.adClickList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeClick);
                }
                Log.YouTube(sb.toString());

                response.success = "OK";
                response.status = true;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("YouTb "+req.bundleId+" Data(geo&&pkg) is null");
                Log.YouTube(sb.toString());

                response.success = "data_error";
                response.status = false;
            }
        }
        System.out.println(time + "    End getYouTbData");
        result.setData(response);
        return result;

    }

    /**
     *YouTb 服务器端推送接口 by Ryan
     * */
    private ResponseBean getYouTbPush(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getYouTbPush");
        ResponseBean result = new ResponseBean(1);
        YouTbProto.YouTbSendPushRequest req = request.getData(new YouTbProto.YouTbSendPushRequest());
        YouTbProto.YouTbSendPushResponse response = new YouTbProto.YouTbSendPushResponse();

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
                Log.YouTube(sb.toString());
                response.success = "OK";
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("YouTbPush(geo&&pkg) is null");
                Log.YouTube(sb.toString());
                response.success = "NO";
            }
            if (!StringUtil.isBlank(tokens) && !StringUtil.isBlank(pkg) && !StringUtil.isBlank(idfa)){
                tokens = tokens.trim();
                idfa = idfa.trim();
                try {
                    AdTokensDao adTokens = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
                    List<AdTokens> tokenslist = adTokens.findByPkg(pkg);
                    System.out.println("tokenslist.size  "+tokenslist.size() );
                    if(tokenslist.size() > 0){
                        for(AdTokens adk : tokenslist){
                            if(adk.getPkg().equals(pkg) && !adk.getIdfa().equals(idfa)){
                                AdTokens adto = new AdTokens();
                                adto.setPkg(pkg);
                                adto.setIdfa(idfa);
                                adto.setTokens(tokens);
                                adto.setStatus(0);
                                int rows = adTokens.insert(adto);
                                if (rows > 0){
                                    System.out.println(time+ "  "+pkg+" 同pkg新idfa    AdTokens insert  "+ rows +"  num");
                                    break;
                                }else {
                                    System.out.println(time+ "  "+pkg+ " 无同pkg新idfa");
                                }
                            }
                            if (adk.getIdfa().equals(idfa) && adk.getPkg().equals(pkg)){
                                try {
                                    int num = adTokens.updateByPkgAndIdfa(pkg,idfa);
                                    if(num > 0){
                                        System.out.println(time + "  "+pkg+ " (重复idfa下架)    Adtokens update  " + num + " num");
                                    }
                                }catch (Exception e){

                                }
                                try {
                                    AdTokens adto = new AdTokens();
                                    adto.setPkg(pkg);
                                    adto.setIdfa(idfa);
                                    adto.setTokens(tokens);
                                    adto.setStatus(0);
                                    int rows = adTokens.insert(adto);
                                    if (rows > 0){
                                        System.out.println(time+  "  "+pkg+" （重复idfa下架后存入最新tokens）    AdTokens insert  "+ rows +"  num");
                                        break;
                                    }else {
                                        System.out.println(time+  "  "+pkg+" 同pkg无重复idfa");
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
                            System.out.println(time+ "  "+pkg+" 第一次插入Tokens");
                        }else {
                            System.out.println(time+ "  "+pkg+ " 无插入Tokens");
                        }
                    }
                }catch (Exception e){

                }
            }

        }
        response.sendTime = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + "    End getYouTbPush");
        return result;
    }


}
