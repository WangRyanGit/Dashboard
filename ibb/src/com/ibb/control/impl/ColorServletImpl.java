package com.ibb.control.impl;

import com.apple.ios.protos.nano.ColorProto;
import com.factory.SrcFactory;
import com.ibb.bean.*;
import com.ibb.control.ColorServlet;
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
 * Created by Ryan on 2017/4/12.
 */
public class ColorServletImpl extends ColorServlet {
    private static final long serialVersionUID = 8586672193764996499L;

    @Override
    public ResponseBean process(RequestBean request) {
        int src = request.getReqType();
        ResponseBean ret = null;
        switch (src) {
            case SrcFactory.SRC_COLOR_PNG:
                ret = getColorPhoto(request);
                break;
            case SrcFactory.SRC_COLOR_REGISTER:
                ret = getColorRegistration(request);
                break;
            case SrcFactory.SRC_COLOR_STRATEGY:
                ret = getColorStrategy(request);
                break;
            case SrcFactory.SRC_COLOR_DATA:
                ret = getColorData(request);
                break;
            case SrcFactory.SRC_COLOR_SENDPUSH:
                ret = getColorPush(request);
                break;
        }
        return ret;
    }

    //接口：http://i.iosfreemusic.com/ibbios/color/42 -- 46

    /**
     * Color 画图本图片源接口 by Ryan
     * */
    private ResponseBean getColorPhoto(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ResponseBean result = new ResponseBean(1);
        System.out.println(time + "    Start getColorPhoto");
        ColorProto.ColorPhotosRequest req = request.getData(new ColorProto.ColorPhotosRequest());
        ColorProto.ColorPhotosResponse response = new ColorProto.ColorPhotosResponse();

        if (req != null){
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.beginPosition).append("\t");
            sb.append(req.number).append("\t");
            sb.append(req.listType).append("\t");
            sb.append(req.photoType);
            Log.Color(sb.toString());
        }
        ColorPhotosDao colorPhotosDao = (ColorPhotosDao) SpringHelper.getBean("dColorPhotosDao");
        Integer beginPosition = 0;
        Integer number = 0;
        String listType = null;
        String photoType = null;
        try{
            beginPosition = req.beginPosition;
            number = req.number;
            listType = req.listType;
            photoType = req.photoType;
        }catch (Exception e){

        }
        if(!StringUtil.isBlank(listType) && listType.equals("title")) {
            List<ColorProto.TypePhotos> typeList = new ArrayList<>();
            List<ColorPhotos> typelist = colorPhotosDao.findTypeAndExample(beginPosition, number);
            if (typelist.size() > 0) {
                for (ColorPhotos item : typelist) {
                    ColorProto.TypePhotos types = new ColorProto.TypePhotos();
                    types.example = item.getExample();
                    types.type = item.getType();
                    typeList.add(types);
                }
            }
            //防止空字符 给ColorPhotosList 传无效字符
            List<ColorProto.ColorPhotos> colorPhotosList = new ArrayList<>();
            ColorProto.ColorPhotos photos = new ColorProto.ColorPhotos();
            photos.example = "invalid";
            photos.type = "invalid";
            photos.photoName="invalid";
            photos.photoUrl="invalid";
            photos.photoId=1L;
            colorPhotosList.add(photos);

            response.colorPhotosList = colorPhotosList.toArray(new ColorProto.ColorPhotos[0]);//无效 不使用
            response.typePhotosList = typeList.toArray(new ColorProto.TypePhotos[0]);
            response.status = "OK";
        }else if(!StringUtil.isBlank(photoType) && listType.equals("content")){
            List<ColorProto.ColorPhotos> colorPhotosList = new ArrayList<>();
            List<ColorPhotos> colorlist = colorPhotosDao.findNameAndPhoto(photoType,beginPosition, number);
            if (colorlist.size() > 0) {
                for (ColorPhotos item : colorlist) {
                    ColorProto.ColorPhotos photos = new ColorProto.ColorPhotos();
                    photos.example = item.getExample();
                    photos.type = item.getType();
                    photos.photoName = item.getPhotoName();
                    photos.photoUrl = item.getPhotoUrl();
                    photos.photoId = item.getId();
                    colorPhotosList.add(photos);
                }
            }
            //防止空字符 TypePhotos 传无效字符
            List<ColorProto.TypePhotos> typeList = new ArrayList<>();
            ColorProto.TypePhotos types = new ColorProto.TypePhotos();
            types.example = "invalid";
            types.type = "invalid";
            typeList.add(types);

            response.colorPhotosList = colorPhotosList.toArray(new ColorProto.ColorPhotos[0]);
            response.typePhotosList = typeList.toArray(new ColorProto.TypePhotos[0]);
            response.status = "OK";
        }else {
            System.out.println("photoType or listType is null");
            response.status = "NO";
        }
        if(response == null){
            response = new ColorProto.ColorPhotosResponse();
        }
        response.serverTime = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time+ "    End getColorPhoto");
        return result;
    }


    /**
     * Color 用户注册接口 by Ryan
     * */
    private ResponseBean getColorRegistration(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getColorRegistration");
        ResponseBean result = new ResponseBean(1);
        ColorProto.ColorRegistrationRequest req = request.getData(new ColorProto.ColorRegistrationRequest());
        ColorProto.ColorRegistrationResponse response = new ColorProto.ColorRegistrationResponse();

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
                Log.Color(sb.toString());

                String userid = Md5.md5(String.valueOf(System.currentTimeMillis()));
                response.userId = userid;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("Color "+ req.bundleId +" registration info（geo&&pkg）is null");
                Log.Color(sb.toString());
            }
        }

        response.timeStamp = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time+ "    End getColorRegistration");
        return result;
    }

    /**
     *Color 广告下发策略接口 by Ryan
     * */
    private ResponseBean getColorStrategy(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getColorStrategy");
        ResponseBean result = new ResponseBean(1);
        ColorProto.ColorStrategyRequest req = request.getData(new ColorProto.ColorStrategyRequest());
        ColorProto.ColorStrategyResponse response = new ColorProto.ColorStrategyResponse();

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
            Log.Color(sb.toString());

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
            List<ColorProto.AdControl> adControlList = new ArrayList<>();
            List<ColorProto.AdStrategy> adStrategyList = new ArrayList<>();
            List<ColorProto.AdClick> adClicksList = new ArrayList<>();
            List<ColorProto.AdResources> adResourcesList = new ArrayList<>();

            if(!StringUtil.isBlank(geo)){
                List<AdControl> adControls = adControlDao.findByPkgAndContry(pkg,geo);
                List<AdStrategy> adStrategies = adStrategyDao.findByPkgAndCountry(pkg,geo);
                List<AdClick> adClicks = adClickDao.findByPkgAndCountry(pkg,geo);
                List<AdResources> adResources = adResourcesDao.findByPkgAndCountry(pkg,geo);

                for(AdControl item : adControls){
                    ColorProto.AdControl control = new ColorProto.AdControl();
                    control.positionID = item.getPosition();
                    control.init = item.getInitOn();
                    control.show = item.getShowOn();
                    control.requestIntervalTime = item.getRequestInterval();
                    control.status = item.getStatus();
                    adControlList.add(control);
                }
                for(AdStrategy item : adStrategies){
                    ColorProto.AdStrategy adStrategy = new ColorProto.AdStrategy();
                    adStrategy.adType = item.getAdType();
                    adStrategy.adSource = item.getAdSource();
                    adStrategy.adID = item.getAdId();
                    adStrategy.positionID = item.getPosition();
                    adStrategy.priority = item.getPriority();
                    adStrategy.adreward = item.getAdreward();
                    adStrategyList.add(adStrategy);
                }
                for (AdClick item : adClicks){
                    ColorProto.AdClick click = new ColorProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
                for (AdResources ad : adResources){
                    ColorProto.AdResources adResources1 = new ColorProto.AdResources();
                    adResources1.sourceKey = ad.getSourcekey();
                    adResources1.typeKey = ad.getTypekey();
                    adResources1.type = ad.getType();
                    adResourcesList.add(adResources1);
                }
            }

            if(adControlList.size() <= 0){
                List<AdControl> allAdcontrol = adControlDao.findByPkgAndContry(pkg,"ALL");
                for(AdControl item : allAdcontrol){
                    ColorProto.AdControl control = new ColorProto.AdControl();
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
                    ColorProto.AdStrategy adStrategy = new ColorProto.AdStrategy();
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
                    ColorProto.AdClick click = new ColorProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
            }
            if(adResourcesList.size() <= 0){
                List<AdResources> allResources = adResourcesDao.findByPkgAndCountry(pkg,"ALL");
                for(AdResources item : allResources){
                    ColorProto.AdResources resources = new ColorProto.AdResources();
                    resources.sourceKey = item.getSourcekey();  //key
                    resources.typeKey = item.getTypekey();  //key的种类
                    resources.type = item.getType();    //使用哪种key
                    adResourcesList.add(resources);
                }
            }
            response.adControlList = adControlList.toArray(new ColorProto.AdControl[0]);
            response.adStrategyList = adStrategyList.toArray(new ColorProto.AdStrategy[0]);
            response.adClickList = adClicksList.toArray(new ColorProto.AdClick[0]);
            response.adResourcesList = adResourcesList.toArray(new ColorProto.AdResources[0]);
        }

        response.serverTime = System.currentTimeMillis();

        if(response == null){
            response = new ColorProto.ColorStrategyResponse();
        }
        result.setData(response);
        System.out.println(time + "    End getColorStrategy");
        return result;
    }

    /**
     *Color 数据统计接口 by Ryan
     * */
    private ResponseBean getColorData(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getColorData");
        ResponseBean result = new ResponseBean(1);
        ColorProto.ColorDataRequest req = request.getData(new ColorProto.ColorDataRequest());
        ColorProto.ColorDataResponse response = new ColorProto.ColorDataResponse();

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
                for(ColorProto.eventApplicationStartup item : req.applicationStartupList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeStartup).append("\t");
                }
                sb.append("eventPageSwitching").append("\t");
                for(ColorProto.eventPageSwitching item : req.pageSwitchingList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.pageName).append("\t");
                    sb.append(item.timeSwitching).append("\t");
                }
                sb.append("eventAdRequest").append("\t");
                for(ColorProto.eventAdRequest item : req.adRequestList) {
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeRequest).append("\t");
                }
                sb.append("eventAdShow").append("\t");
                for(ColorProto.eventAdShow item : req.adShowList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeShow).append("\t");
                }
                sb.append("eventAdClick").append("\t");
                for(ColorProto.eventAdClick item : req.adClickList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeClick);
                }
                Log.Color(sb.toString());

                response.success = "OK";
                response.status = true;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("Color "+req.bundleId+" Data(geo&&pkg) is null");
                Log.Color(sb.toString());

                response.success = "data_error";
                response.status = false;
            }
        }
        System.out.println(time + "    End getColorData");
        result.setData(response);
        return result;

    }

    /**
     *Color 服务器端推送接口 by Ryan
     * */
    private ResponseBean getColorPush(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getColorPush");
        ResponseBean result = new ResponseBean(1);
        ColorProto.ColorSendPushRequest req = request.getData(new ColorProto.ColorSendPushRequest());
        ColorProto.ColorSendPushResponse response = new ColorProto.ColorSendPushResponse();

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
                Log.Color(sb.toString());
                response.success = "OK";
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("ColorPush(geo&&pkg) is null");
                Log.Color(sb.toString());
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
        System.out.println(time + "    End getColorPush");
        return result;
    }

}
