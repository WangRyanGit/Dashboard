package com.ibb.control.impl;

import com.apple.ios.protos.nano.FollowProto;
import com.factory.SrcFactory;
import com.ibb.bean.*;
import com.ibb.control.FollowServlet;
import com.ibb.dao.*;
import com.ibb.ios.IOS_Verify;
import com.ibb.util.BuyUtil;
import com.ibb.util.DateUtil;
import com.ibb.util.StringUtil;
import com.log.Log;
import com.util.Md5;
import com.util.SpringHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 2017/4/17.
 */
public class FollowServletImpl extends FollowServlet {

    private static final long serialVersionUID = -5870297790766213169L;

    @Override
    public ResponseBean process(RequestBean request) {
        int src = request.getReqType();
        ResponseBean ret = null;
        switch (src) {
            case SrcFactory.SRC_FOLLOW_REGISTER:
                ret = getFollowRegistration(request);
                break;
            case SrcFactory.SRC_FOLLOW_STRATEGY:
                ret = getFollowStrategy(request);
                break;
            case SrcFactory.SRC_FOLLOW_DATA:
                ret = getFollowData(request);
                break;
            case SrcFactory.SRC_FOLLOW_SENDPUSH:
                ret = getFollowPush(request);
                break;
            case SrcFactory.SRC_FOLLOW_IAP:
                ret = getFollowIap(request);
                break;
            case SrcFactory.SRC_FOLLOW_EVENT:
                ret = getFollowEvent(request);
                break;
        }
        return ret;
    }

    //接口：http://i.iosfreemusic.com/ibbios/follow/50 -- 55
    /**
     * Follow 用户注册接口 by Ryan
     * */
    private ResponseBean getFollowRegistration(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getFollowRegistration");
        ResponseBean result = new ResponseBean(1);
        FollowProto.FollowRegistrationRequest req = request.getData(new FollowProto.FollowRegistrationRequest());
        FollowProto.FollowRegistrationResponse response = new FollowProto.FollowRegistrationResponse();

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
                Log.Follow(sb.toString());

                String userid = Md5.md5(String.valueOf(System.currentTimeMillis()));
                response.userId = userid;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("Follow "+ req.bundleId +" registration info（geo&&pkg）is null");
                Log.Follow(sb.toString());
            }
        }

        response.timeStamp = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time+ "    End getFollowRegistration");
        return result;
    }

    /**
     *Follow 广告下发策略接口 by Ryan
     * */
    private ResponseBean getFollowStrategy(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getFollowStrategy");
        ResponseBean result = new ResponseBean(1);
        FollowProto.FollowStrategyRequest req = request.getData(new FollowProto.FollowStrategyRequest());
        FollowProto.FollowStrategyResponse response = new FollowProto.FollowStrategyResponse();

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
            Log.Follow(sb.toString());

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
            List<FollowProto.AdControl> adControlList = new ArrayList<>();
            List<FollowProto.AdStrategy> adStrategyList = new ArrayList<>();
            List<FollowProto.AdClick> adClicksList = new ArrayList<>();
            List<FollowProto.AdResources> adResourcesList = new ArrayList<>();

            if(!StringUtil.isBlank(geo)){
                List<AdControl> adControls = adControlDao.findByPkgAndContry(pkg,geo);
                List<AdStrategy> adStrategies = adStrategyDao.findByPkgAndCountry(pkg,geo);
                List<AdClick> adClicks = adClickDao.findByPkgAndCountry(pkg,geo);
                List<AdResources> adResources = adResourcesDao.findByPkgAndCountry(pkg,geo);

                for(AdControl item : adControls){
                    FollowProto.AdControl control = new FollowProto.AdControl();
                    control.positionID = item.getPosition();
                    control.init = item.getInitOn();
                    control.show = item.getShowOn();
                    control.requestIntervalTime = item.getRequestInterval();
                    control.status = item.getStatus();
                    adControlList.add(control);
                }
                for(AdStrategy item : adStrategies){
                    FollowProto.AdStrategy adStrategy = new FollowProto.AdStrategy();
                    adStrategy.adType = item.getAdType();
                    adStrategy.adSource = item.getAdSource();
                    adStrategy.adID = item.getAdId();
                    adStrategy.positionID = item.getPosition();
                    adStrategy.priority = item.getPriority();
                    adStrategy.adreward = item.getAdreward();
                    adStrategyList.add(adStrategy);
                }
                for (AdClick item : adClicks){
                    FollowProto.AdClick click = new FollowProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
                for (AdResources ad : adResources){
                    FollowProto.AdResources adResources1 = new FollowProto.AdResources();
                    adResources1.sourceKey = ad.getSourcekey();
                    adResources1.typeKey = ad.getTypekey();
                    adResources1.type = ad.getType();
                    adResourcesList.add(adResources1);
                }
            }

            if(adControlList.size() <= 0){
                List<AdControl> allAdcontrol = adControlDao.findByPkgAndContry(pkg,"ALL");
                for(AdControl item : allAdcontrol){
                    FollowProto.AdControl control = new FollowProto.AdControl();
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
                    FollowProto.AdStrategy adStrategy = new FollowProto.AdStrategy();
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
                    FollowProto.AdClick click = new FollowProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
            }
            if(adResourcesList.size() <= 0){
                List<AdResources> allResources = adResourcesDao.findByPkgAndCountry(pkg,"ALL");
                for(AdResources item : allResources){
                    FollowProto.AdResources resources = new FollowProto.AdResources();
                    resources.sourceKey = item.getSourcekey();  //key
                    resources.typeKey = item.getTypekey();  //key的种类
                    resources.type = item.getType();    //使用哪种key
                    adResourcesList.add(resources);
                }
            }
            response.adControlList = adControlList.toArray(new FollowProto.AdControl[0]);
            response.adStrategyList = adStrategyList.toArray(new FollowProto.AdStrategy[0]);
            response.adClickList = adClicksList.toArray(new FollowProto.AdClick[0]);
            response.adResourcesList = adResourcesList.toArray(new FollowProto.AdResources[0]);
        }

        response.serverTime = System.currentTimeMillis();

        if(response == null){
            response = new FollowProto.FollowStrategyResponse();
        }
        result.setData(response);
        System.out.println(time + "    End getFollowStrategy");
        return result;
    }

    /**
     *Follow 数据统计接口 by Ryan
     * */
    private ResponseBean getFollowData(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getFollowData");
        ResponseBean result = new ResponseBean(1);
        FollowProto.FollowDataRequest req = request.getData(new FollowProto.FollowDataRequest());
        FollowProto.FollowDataResponse response = new FollowProto.FollowDataResponse();

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
                for(FollowProto.eventApplicationStartup item : req.applicationStartupList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeStartup).append("\t");
                }
                sb.append("eventPageSwitching").append("\t");
                for(FollowProto.eventPageSwitching item : req.pageSwitchingList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.pageName).append("\t");
                    sb.append(item.timeSwitching).append("\t");
                }
                sb.append("eventAdRequest").append("\t");
                for(FollowProto.eventAdRequest item : req.adRequestList) {
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeRequest).append("\t");
                }
                sb.append("eventAdShow").append("\t");
                for(FollowProto.eventAdShow item : req.adShowList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeShow).append("\t");
                }
                sb.append("eventAdClick").append("\t");
                for(FollowProto.eventAdClick item : req.adClickList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeClick);
                }
                Log.Follow(sb.toString());

                response.success = "OK";
                response.status = true;
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("Follow "+req.bundleId+" Data(geo&&pkg) is null");
                Log.Follow(sb.toString());

                response.success = "data_error";
                response.status = false;
            }
        }
        System.out.println(time + "    End getFollowData");
        result.setData(response);
        return result;

    }

    /**
     *Follow 服务器端推送接口 by Ryan
     * */
    private ResponseBean getFollowPush(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getFollowPush");
        ResponseBean result = new ResponseBean(1);
        FollowProto.FollowSendPushRequest req = request.getData(new FollowProto.FollowSendPushRequest());
        FollowProto.FollowSendPushResponse response = new FollowProto.FollowSendPushResponse();

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
                Log.Follow(sb.toString());
                response.success = "OK";
            }else {
                StringBuilder sb = new StringBuilder();
                sb.append(time).append("\t");
                sb.append("FollowPush(geo&&pkg) is null");
                Log.Follow(sb.toString());
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
        System.out.println(time + "    End getFollowPush");
        return result;
    }

    /**
     * follow iap二次验证
     * */
    private ResponseBean getFollowIap(RequestBean request) {
        /**
         * 客户端向服务器验证
         *
         *
         *   * checkState  A  验证成功有效(返回收据)
         *             B  账单有效，但己经验证过
         *             C  服务器数据库中没有此账单(无效账单)
         *             D  不处理
         *
         * @return
         * @throws IOException
         */
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getFollowIap");
        ResponseBean result = new ResponseBean(1);
        FollowProto.FollowIapVerifyRequest req = request.getData(new FollowProto.FollowIapVerifyRequest());
        FollowProto.FollowIapVerifyResponse response = new FollowProto.FollowIapVerifyResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.userId).append("\t");
            sb.append(req.voucher);
            Log.Follow(sb.toString());
        }
        FollowPurchaseDao followPurchaseDao = (FollowPurchaseDao) SpringHelper.getBean("dVFollowPurchaseDao");
        String pkg = req.bundleId;
        String userid = req.userId;
        //苹果客户端传上来的收据,是最原据的收据
        String receipt = req.voucher;
        System.out.println("来自苹果端的验证...");
        String password = null;
        if(pkg.equals("com.shadowfollow.com")){
            password = "4e8cf876984f4e7390ae9fcff39281bc";//共享秘钥
        }else if(pkg.equals("com.followersLife.app")){
            password = "45482aa99a8f4c129d3b66efca617539";
        }else if(pkg.equals("com.follow.app")){
            password = "f9dc3337f05744e9815a16a660cc0376";
        }else if(pkg.equals("com.CircleReport.app")) {
            password = "9fcefad6fe344971895a88d67ef5de2c";
        }
        System.out.println("receipt  "+receipt);
        if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(userid) && !StringUtil.isBlank(receipt)) {
            //拿到收据的MD5
            String md5_receipt = Md5.md5(receipt);
            //默认是无效账单
            String answer = null;
            answer = BuyUtil.STATE_C + "#" + md5_receipt;
            //查询数据库，看是否是己经验证过的账号
            List<FollowPurchase> pulist = followPurchaseDao.findByMd5Receipt(md5_receipt);  //查库
            String verifyResult = null;
            String verifyUrl = null;
            if (pulist.size() == 0) {
                verifyResult = IOS_Verify.buyAppVerify(receipt, password, verifyUrl);
                System.out.println("verifyResult  " + verifyResult);
                if (verifyResult == null) {
                    //苹果服务器没有返回验证结果
                    answer = BuyUtil.STATE_D + "#" + md5_receipt;
                    response.status = "Not_handle_error";
                } else {
                    //跟苹果验证有返回结果------------------
                    JSONObject job = JSONObject.fromObject(verifyResult);
                    String states = job.getString("status");
                    if (states.equals("0")) {//验证成功
                        String r_receipt = job.getString("receipt");
                        JSONObject returnJson = JSONObject.fromObject(r_receipt);
                        String bundle_id = returnJson.getString("bundle_id");
                        JSONArray array = returnJson.getJSONArray("in_app");
                        JSONObject item = array.getJSONObject(0);
                        //数量
                        String quantity = item.getString("quantity");
                        //产品ID
                        String product_id = item.getString("product_id");
                        //交易日期（时间戳）
                        String purchase_date = item.getString("purchase_date_ms");
                        //到期日期（时间戳）
                        String expires_date = item.getString("expires_date_ms");
                        //跟苹果的服务器验证成功
                        answer = BuyUtil.STATE_A + "#" + md5_receipt + "_" + product_id + "_" + quantity;
                        //保存到数据库
                        //下架follow_purchase表中过期数据
                        int count = followPurchaseDao.updateByUseridAndPkg(pkg,userid);
                        if (count > 0) {
                            System.out.println("下架用户过期付费信息  "+ pkg + "  " + userid);
                        }
                        //将苹果服务器验证信息保存到follow_purchase表
                        FollowPurchase followPurchase = new FollowPurchase();
                        followPurchase.setPkg(pkg);
                        followPurchase.setUser_id(userid);
                        followPurchase.setQuantity(quantity);
                        followPurchase.setProduct_id(product_id);
                        followPurchase.setPurchase_date(purchase_date);
                        followPurchase.setExpires_date(expires_date);
                        followPurchase.setMd5_receipt(md5_receipt);
                        followPurchase.setStatus(0);
                        int rows = followPurchaseDao.insert(followPurchase);
                        if (rows > 0) {
                            System.out.println("验证结果 " + pkg + " answer " + answer + "  IOSVerify success");
                            List<FollowProto.FollowVerify> FollowVerifyList = new ArrayList<>();
                            FollowProto.FollowVerify verify = new FollowProto.FollowVerify();
                            verify.userId = userid;
                            verify.bundleId = pkg;
                            verify.quantity = quantity;
                            verify.productId = product_id;
                            verify.purchaseDate = purchase_date;
                            verify.expiresDate = expires_date;
                            verify.md5Receipt = md5_receipt;
                            FollowVerifyList.add(verify);
                            response.followVerifyList = FollowVerifyList.toArray(new FollowProto.FollowVerify[0]);
                            response.status = "OK_Validation_success";
                        }
                    } else if (states.equals("21007")) {
                        verifyUrl = "Sandbox";
                        verifyResult = IOS_Verify.buyAppVerify(receipt, password, verifyUrl);
                        System.out.println(verifyResult);
                        //跟苹果验证有返回结果------------------
                        JSONObject json = JSONObject.fromObject(verifyResult);
                        String state = json.getString("status");
                        if (state.equals("0")) {//验证成功
                            String r_receipt = json.getString("receipt");
                            JSONObject returnJson = JSONObject.fromObject(r_receipt);
                            String bundle_id = returnJson.getString("bundle_id");
                            JSONArray array = returnJson.getJSONArray("in_app");
                            JSONObject item = array.getJSONObject(0);
                            //数量
                            String quantity = item.getString("quantity");
                            //产品ID
                            String product_id = item.getString("product_id");
                            //交易日期（时间戳）
                            String purchase_date = item.getString("purchase_date_ms");
                            //到期日期（时间戳）
                            String expires_date = item.getString("expires_date_ms");
                            //跟苹果的服务器验证成功
                            answer = BuyUtil.STATE_A + "#" + md5_receipt + "_" + product_id + "_" + quantity;
                            //保存到数据库
                            //下架follow_purchase表中过期数据
                            int count = followPurchaseDao.updateByUseridAndPkg(pkg,userid);
                            if (count > 0) {
                                System.out.println("下架用户过期付费信息  "+ pkg + "  " + userid);
                            }
                            //将苹果服务器验证信息保存到follow_purchase表
                            FollowPurchase followPurchase = new FollowPurchase();
                            followPurchase.setPkg(pkg);
                            followPurchase.setUser_id(userid);
                            followPurchase.setQuantity(quantity);
                            followPurchase.setProduct_id(product_id);
                            followPurchase.setPurchase_date(purchase_date);
                            followPurchase.setExpires_date(expires_date);
                            followPurchase.setMd5_receipt(md5_receipt);
                            followPurchase.setStatus(0);
                            int rows = followPurchaseDao.insert(followPurchase);
                            if (rows > 0) {
                                System.out.println("验证结果 " + pkg + " answer " + answer + "  IOSVerify success");
                                List<FollowProto.FollowVerify> FollowVerifyList = new ArrayList<>();
                                FollowProto.FollowVerify verify = new FollowProto.FollowVerify();
                                verify.userId = userid;
                                verify.bundleId = pkg;
                                verify.quantity = quantity;
                                verify.productId = product_id;
                                verify.purchaseDate = purchase_date;
                                verify.expiresDate = expires_date;
                                verify.md5Receipt = md5_receipt;
                                FollowVerifyList.add(verify);
                                response.followVerifyList = FollowVerifyList.toArray(new FollowProto.FollowVerify[0]);
                                response.status = "OK_Validation_success";
                            }
                        }
                    } else {
                        //账单无效
                        answer = BuyUtil.STATE_C + "#" + md5_receipt;
                        response.status = "NO_Invalid_bill_error";
                    }
                    //跟苹果验证有返回结果------------------
                }
                //传上来的收据有购买信息==end=============
            } else {
                //账单有效，但己验证过
                answer = BuyUtil.STATE_B + "#" + md5_receipt;
                System.out.println("验证结果 " + pkg + " answer " + answer + "  账单有效，但己验证过");
                response.status = "OK_Bill_validated";
            }
        }else{
            response.status = "data_error";
        }
        //返回结果
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " "+ pkg + "    End getFollowIap");
        return result;
    }


    /**
     * follow 回返事件
     * */
    private ResponseBean getFollowEvent(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getFollowEvent");
        ResponseBean result = new ResponseBean(1);
        FollowProto.FollowEventRequest req = request.getData(new FollowProto.FollowEventRequest());
        FollowProto.FollowEventResponse response = new FollowProto.FollowEventResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.userId);
            Log.Follow(sb.toString());
        }
        FollowPurchaseDao followPurchaseDao = (FollowPurchaseDao) SpringHelper.getBean("dVFollowPurchaseDao");
        String pkg = req.bundleId;
        String userId = req.userId;
        try {
            if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(userId) ) {
                List<FollowPurchase> purchaselist = followPurchaseDao.findByPkgAndUserid(pkg,userId);
                List<FollowProto.FollowVerify> verifyList = new ArrayList<>();

                if (purchaselist.size() > 0){
                    for(FollowPurchase item : purchaselist){
                        FollowProto.FollowVerify verify = new FollowProto.FollowVerify();
                        verify.bundleId = item.getPkg();
                        verify.userId = item.getUser_id();
                        verify.quantity = item.getQuantity();
                        verify.productId = item.getProduct_id();
                        verify.purchaseDate = item.getPurchase_date();
                        verify.expiresDate = item.getExpires_date();
                        verify.md5Receipt = item.getMd5_receipt();
                        verifyList.add(verify);
                    }
                }else {
                    FollowProto.FollowVerify verify = new FollowProto.FollowVerify();
                    verify.bundleId = pkg;
                    verify.userId = userId;
                    verify.quantity = "Invalid";
                    verify.productId = "Invalid";
                    verify.purchaseDate = "Invalid";
                    verify.expiresDate = "Invalid";
                    verify.md5Receipt = "Invalid";
                    verifyList.add(verify);
                }
                response.followVerifyList = verifyList.toArray(new FollowProto.FollowVerify[0]);
                System.out.println("用户 "+pkg+" " +userId + "  event success");
                response.status = "OK";
            }else {
                System.out.println("event data error");
                response.status = "data_error";
            }
        }catch (Exception e) {

        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " "+ pkg + "    End getFollowEvent");
        return result;
    }


}
