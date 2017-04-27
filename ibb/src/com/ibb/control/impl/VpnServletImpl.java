package com.ibb.control.impl;

import com.apple.ios.protos.nano.VpnProto;
import com.factory.SrcFactory;
import com.ibb.bean.*;
import com.ibb.control.VpnServlet;
import com.ibb.dao.*;
import com.ibb.ios.IOS_Verify;
import com.ibb.mail.JavaMailWithAttachment;
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
 * Created by Ryan on 2017/3/21.
 */
public class VpnServletImpl extends VpnServlet {

    private static final long serialVersionUID = 686248604060992980L;

    @Override
    public ResponseBean process(RequestBean request) {
        int src = request.getReqType();
        ResponseBean ret = null;
        switch (src) {
            case SrcFactory.SRC_VPN_SERVER:
                ret = getVpnServers(request);
                break;
            case SrcFactory.SRC_VPN_REGISTER:
                ret = getVpnRegister(request);
                break;
            case SrcFactory.SRC_VPN_LOGIN:
                ret = getVpnLogin(request);
                break;
            case SrcFactory.SRC_VPN_CHANGE:
                ret = getVpnChange(request);
                break;
            /*case SrcFactory.SRC_VPN_PAY:
                ret = getVpnPay(request);
                break;*/
            case SrcFactory.SRC_VPN_SIGN:
                ret = getVpnSign(request);
                break;
            case SrcFactory.SRC_VPN_EVENT:
                ret = getVpnEvent(request);
                break;
            case SrcFactory.SRC_VPN_CODE:
                ret = getVpnCode(request);
                break;
            case SrcFactory.SRC_VPN_APPLEID:
                ret = getVpnAppleId(request);
                break;
            case SrcFactory.SRC_VPN_BUYVERIFY:
                ret = getIOSVerify(request);
                break;
            case SrcFactory.SRC_VPN_STRATEGY:
                ret = getVpnStrategy(request);
                break;
            case SrcFactory.SRC_VPN_DATA:
                ret = getVpnData(request);
                break;
            case SrcFactory.SRC_VPN_SENDPUSH:
                ret = getVpnPush(request);
                break;
        }
        return ret;
    }

    //接口：http://i.iosfreemusic.com/ibbios/vpn/20 -- 34

    /**
     * vpn servers接口
     * */
    private ResponseBean getVpnServers(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getVpnServers");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnServersRequest req = request.getData(new VpnProto.VpnServersRequest());
        VpnProto.VpnServersResponse response = new VpnProto.VpnServersResponse();

        if (req != null){
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId);
            Log.vpnserver(sb.toString());
        }
        VpnServersDao vpnServersDao = (VpnServersDao) SpringHelper.getBean("dVpnServersDao");
        VpnConfigDao vpnConfigDao = (VpnConfigDao) SpringHelper.getBean("dVpnConfigDao");
        String pkg = null;
        try{
            pkg = req.bundleId;
        }catch (Exception e){

        }
        if(!StringUtil.isBlank(pkg)){
            List<VpnProto.VpnServers> vpnServersList = new ArrayList<>();
            List<VpnProto.VpnConfig> vpnConfigList = new ArrayList<>();
            if(vpnServersList.size() <= 0){
                List<VpnServers> serverlist = vpnServersDao.findAll();
                List<VpnServers> vpnServers = new ArrayList<>();
                //等待排序
                try{
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
                                vpnServers.add(vp);
                            }else {
                                num =1;  //不相同 初始化1
                                coun = vpn.getCountry();
                                vp.setCountry(vpn.getCountry());
                                vp.setIp(vpn.getIp());
                                vp.setServer_load(String.valueOf(num));
                                vp.setFree(vpn.getFree());
                                vp.setFlagurl(vpn.getFlagurl());
                                vpnServers.add(vp);
                            }
                            num++;
                        }else {
                            VpnServers vp = new VpnServers();
                            vp.setCountry(vpn.getCountry());
                            vp.setIp(vpn.getIp());
                            vp.setServer_load(String.valueOf(row));
                            vp.setFree(vpn.getFree());
                            vp.setFlagurl(vpn.getFlagurl());
                            vpnServers.add(vp);
                        }
                        row++;
                    }
                }catch (Exception e){

                }
                for(VpnServers item : vpnServers){
                    VpnProto.VpnServers servers = new VpnProto.VpnServers();
                    servers.country = item.getCountry();
                    servers.serverLoad = item.getServer_load();
                    servers.ip = item.getIp();
                    servers.flagurl = item.getFlagurl();
                    servers.free = item.getFree();
                    vpnServersList.add(servers);
                }
            }
            if(vpnConfigList.size() <= 0){
                List<VpnConfig> vpnConfig = vpnConfigDao.findAll();
                for(VpnConfig item : vpnConfig){
                    VpnProto.VpnConfig config = new VpnProto.VpnConfig();
                    config.id = item.getId();
                    config.psk = item.getPsk();
                    config.eapUser = item.getEap_user();
                    config.remoteId = item.getRemote_id();
                    config.eapPasswd = item.getEap_passwd();
                    config.localId = item.getLocal_id();
                    config.type = item.getType();
                    vpnConfigList.add(config);
                }
            }
            response.vpnServersList = vpnServersList.toArray(new VpnProto.VpnServers[0]);
            response.vpnConfigList = vpnConfigList.toArray(new VpnProto.VpnConfig[0]);
            response.status = "OK";
        }else {
            System.out.println("vpn servers are null");
            response.status = "NO";
        }

        if(response == null){
            response = new VpnProto.VpnServersResponse();
        }
        response.serverTime = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time+ "    End getVpnServers");
        return result;
    }

    /**
     * vpn 用户注册接口
     * */
    private ResponseBean getVpnRegister(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getVpnRegister");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnRegisterRequest req = request.getData(new VpnProto.VpnRegisterRequest());
        VpnProto.VpnRegisterResponse response = new VpnProto.VpnRegisterResponse();

        if (req != null){
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.username).append("\t");
            sb.append(req.password).append("\t");
            sb.append(req.begintime);
            Log.vpnserver(sb.toString());
        }
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        VpnSignDao vpnSignDao = (VpnSignDao) SpringHelper.getBean("dVpnSignDao");
        String pkg = null;
        String username = null;
        String password = null;
        String begintime= null;
        try{
            pkg = req.bundleId;
            username = req.username;
            password = req.password;
            begintime = req.begintime;
        }catch (Exception e){

        }
        if(!StringUtil.isBlank(pkg) && !StringUtil.isBlank(username)&& !StringUtil.isBlank(password)&& !StringUtil.isBlank(begintime)){
            try{
                //判断用户名是否重复
                List<VpnUsers> user = vpnUsersDao.findNameAndPkg(username,pkg);
                if(user.size() > 0){
                    System.out.println("用户名重复");
                    response.status = "exist_error";
                }else {
                    VpnUsers vpnUsers = new VpnUsers();
                    vpnUsers.setUsername(username);
                    vpnUsers.setPassword(password);
                    vpnUsers.setPkg(pkg);
                    vpnUsers.setRegtime("0");
                    vpnUsers.setStatus(0);
                    int rows = vpnUsersDao.insert(vpnUsers);    //将注册用户插入数据库
                    if (rows > 0){
                        System.out.println("注册用户  "+ pkg + " "+username + " " + password);
                    }

                    List<VpnUsers> listm = vpnUsersDao.findNameAndPkg(username,pkg);  // 根据用户名查找用户信息
                    System.out.println("listm.size  "+listm.size());
                    //判断是否是注册用户
                    if (listm.size() > 0) {
                        long userId = 0;
                        for (VpnUsers item : listm) {
                            userId = item.getId();
                        }
                        Date beginDate = DateUtil.StringToTime(begintime);
                        //新用户 预留将接受来的字符类型免费使用时间转为日期类型
                        Date dateone = DateUtil.getThreeDay(beginDate);   //获取后三天日期
                        String ThreeDay = DateUtil.TimeToString(dateone);
                        VpnSign vp = new VpnSign();
                        vp.setPkg(pkg);
                        vp.setUserId(userId);
                        vp.setBegintime(begintime);
                        vp.setType("try");
                        vp.setEndtime(ThreeDay);
                        vp.setStatus(0);
                        int nums = vpnSignDao.insert(vp);    //将试用用户插入数据库
                        if (nums > 0) {
                            System.out.println("免费试用用户  " + pkg + " " + userId);
                        }

                        response.status = "OK";
                    }

                }
            }catch (Exception e){

            }
        }else {
            System.out.println("vpn users are null");
            response.status = "data_error";
        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + "    End getVpnRegister");
        return result;
    }

    /**
     * vpn 用户登录接口
     * */
    private ResponseBean getVpnLogin(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getVpnLogin");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnLoginRequest req = request.getData(new VpnProto.VpnLoginRequest());
        VpnProto.VpnLoginResponse response = new VpnProto.VpnLoginResponse();

        if (req != null){
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.username).append("\t");
            sb.append(req.password);
            Log.vpnserver(sb.toString());
        }
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        VpnSignDao vpnSignDao = (VpnSignDao) SpringHelper.getBean("dVpnSignDao");
        VpnPurchaseDao vpnPurchaseDao = (VpnPurchaseDao) SpringHelper.getBean("dVpnPurchaseDao");
        String pkg = null;
        String username = null;
        String password = null;
        try{
            pkg = req.bundleId;
            username = req.username;
            password = req.password;
        }catch (Exception e){

        }
        if(!StringUtil.isBlank(pkg) && !StringUtil.isBlank(username)&& !StringUtil.isBlank(password)){
            try{
                List<VpnUsers> vpnUsers = vpnUsersDao.findByNameAndPassAndPkg(username,password,pkg);  // 根据用户名和密码查找用户信息
                List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                //判断是否是注册用户
                if (vpnUsers.size() > 0){
                    Long userId = 0l;
                    for(VpnUsers item : vpnUsers){
                        VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                        userId = item.getId();
                        users.id = item.getId();
                        users.username = item.getUsername();
                        users.password = item.getPassword();
                        users.bundleId = item.getPkg();
                        users.regtime = item.getRegtime();
                        UsersList.add(users);
                    }
                    List<VpnSign> signlist = vpnSignDao.findByPkgAndUserid(pkg,userId);
                    List<VpnProto.VpnSign> signList = new ArrayList<>();
                    for(VpnSign item : signlist){
                        VpnProto.VpnSign users = new VpnProto.VpnSign();
                        users.id = item.getId();
                        users.bundleId = item.getPkg();
                        users.userId = item.getUserId();
                        users.begintime = item.getBegintime();
                        users.type = item.getType();
                        users.endtime = item.getEndtime();
                        signList.add(users);
                    }
                    List<VpnPurchase> purchaselist = vpnPurchaseDao.findByPkgAndUserid(pkg,userId);
                    List<VpnProto.VpnVerify> verifyList = new ArrayList<>();
                    if (purchaselist.size() > 0){
                        for(VpnPurchase item : purchaselist){
                            VpnProto.VpnVerify verify = new VpnProto.VpnVerify();
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
                        VpnProto.VpnVerify verify = new VpnProto.VpnVerify();
                        verify.bundleId = pkg;
                        verify.userId = userId;
                        verify.quantity = "Invalid";
                        verify.productId = "Invalid";
                        verify.purchaseDate = "Invalid";
                        verify.expiresDate = "Invalid";
                        verify.md5Receipt = "Invalid";
                        verifyList.add(verify);
                    }
                    System.out.println("登录成功 "+ pkg + " " + username + " " + password);
                    response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                    response.vpnSignList = signList.toArray(new VpnProto.VpnSign[0]);
                    response.vpnVerifyList = verifyList.toArray(new VpnProto.VpnVerify[0]);
                    response.status = "OK";
                }else {
                    System.out.println("vpn login are error");
                    response.status = "login_error";
                }
            }catch (Exception e) {

            }
        }else {
            System.out.println("vpn login data error");
            response.status = "data_error";
        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " " + pkg  +"    End getVpnLogin");
        return result;
    }

    /**
     * vpn 短信接口
     * */
    private ResponseBean getVpnCode(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getVpnCode");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnCodeRequest req = request.getData(new VpnProto.VpnCodeRequest());
        VpnProto.VpnCodeResponse response = new VpnProto.VpnCodeResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.username);
            Log.vpnserver(sb.toString());
        }
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        String pkg = null;
        String username = null;
        try {
            pkg = req.bundleId;
            username = req.username;
        } catch (Exception e) {

        }
        if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(username)) {
            try {
                List<VpnUsers> vpnUsers = vpnUsersDao.findNameAndPkg(username,pkg);  // 根据name和pkg查找用户信息
                if (vpnUsers.size() > 0) {  //判断是否有此用户
                    //发送邮件验证码
                    System.out.println("Ready to send mail ...");
                    JavaMailWithAttachment se = new JavaMailWithAttachment(false);
                    StringBuilder builder = new StringBuilder();
                    StringBuffer url = new StringBuffer();
                    String user = username;
                    String verify = StringUtil.generateVerifyCode();    //验证码
                    String verifyCode = verify.toLowerCase();
                    System.out.println("验证码  " + verifyCode);
                    url.append("VerifyCode: "+"<font color='red' size='16px'>" + verifyCode + "</font>");
                    // 正文
                    builder.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body><div style=style=\"width: 90%\">");
                    builder.append("<span><font color='green'>"+"Hello  "+ user + "</font></span>");
                    builder.append("<br/><br/>");
                    builder.append("<span>"+"We received a reset password request for your account.Please enter the following characters in the validation box to complete the reset password operation."+ "</span>");
                    builder.append("<br/><br/>");
                    builder.append("<span>"+"VerifyCode: " + url + "</span>");
                    builder.append("<br/><br/>");
                    builder.append("<span>"+"If you did not make this request, please report this suspicious activity to alicexiong0707@gmail.com."+"</span>");
                    builder.append("<br/><br/><br/>");
                    builder.append("<span>"+"Regards,"+"</span><br/>");
                    builder.append("<span>"+"ActiveVPN team"+"</span>");
                    builder.append("</div></body></html>");
                    String title = "ActiveVPN Reset Password";
                    String email = user;
                    se.doSendHtmlEmail(title, builder.toString(), email, null);
                    System.out.println("sendMail end!");

                    response.verifyCode = verifyCode;   //返回验证码
                    response.status = "OK";
                    //验证码发送结束
                }else {
                    System.out.println("vpn send mail error");
                    response.status = "send_error";
                }
            } catch (Exception e) {

            }
        }else {
            System.out.println("vpn send mail data_null error");
            response.status = "data_error";
        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " " + pkg  +"    End getVpnCode");
        return result;
    }

    /**
     * vpn 忘记密码接口
     * */
    private ResponseBean getVpnChange(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getVpnChange");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnChangeRequest req = request.getData(new VpnProto.VpnChangeRequest());
        VpnProto.VpnChangeResponse response = new VpnProto.VpnChangeResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.username);
            Log.vpnserver(sb.toString());
        }
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        String pkg = null;
        String username = null;
        String newpass = null;
        try {
            pkg = req.bundleId;
            username = req.username;
            newpass = req.newpassword;
        } catch (Exception e) {

        }
        if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(username)) {
            try {
                List<VpnUsers> vpnUsers = vpnUsersDao.findNameAndPkg(username,pkg);  // 根据name和pkg查找用户信息
                if (vpnUsers.size() > 0) {  //判断是否有此用户
                    for (VpnUsers item : vpnUsers) {
                        int rows = vpnUsersDao.updatePassByPkgName(newpass,username,item.getPassword(),pkg);
                        if (rows >0){
                            System.out.println("修改密码成功 "+ pkg + " "+username + "  " +newpass);
                        }
                        List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                        VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                        users.id = item.getId();
                        users.username = item.getUsername();
                        users.password = newpass;
                        users.bundleId = item.getPkg();
                        users.regtime = item.getRegtime();
                        UsersList.add(users);
                        response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                        response.status = "OK";
                    }
                }else {
                System.out.println("vpn change user password are error");
                response.status = "name_error";
                }
            } catch (Exception e) {

            }
        }else {
            System.out.println("vpn change user password data error");
            response.status = "data_error";
        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " " + pkg  +"    End getVpnChange");
        return result;
    }


    /**
     * vpn 付费
     * */
    /*private ResponseBean getVpnPay(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getVpnPay");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnPayRequest req = request.getData(new VpnProto.VpnPayRequest());
        VpnProto.VpnPayResponse response = new VpnProto.VpnPayResponse();
        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.regtime).append("\t");
            sb.append(req.begintime).append("\t");
            sb.append(req.id);
            Log.vpnserver(sb.toString());
        }
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        String pkg = req.bundleId;
        Long id = req.id;
        String begintime = req.begintime;
        String regtime = req.regtime;
        String endtime = null;

        if (!StringUtil.isBlank(pkg) && id != null && Integer.valueOf(regtime) > 0 && !StringUtil.isBlank(begintime)) {
            try {
                Date beginDate = DateUtil.StringToTime(begintime);   //预留将接受来的字符类型付费时间转为日期类型
                String one = "1";
                String three = "3";
                String six = "6";
                String twelve = "12";
                if(regtime.equals(one)){
                    Date dateone = DateUtil.getOneMONTH(beginDate);   //获取后一个月日期
                    String oneMonth = DateUtil.TimeToString(dateone);
                    endtime = oneMonth;
                    int rows = vpnUsersDao.updatePay(begintime,one,endtime,id);
                    if (rows > 0){
                        System.out.println("用户 "+ pkg + " "+id + " 付费 "+one+ " 月");
                    }
                    List<VpnUsers> vpnUsers = vpnUsersDao.findById(id);  // 根据id查找用户信息
                    for(VpnUsers item : vpnUsers) {
                        List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                        VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                        users.id = item.getId();
                        users.username = item.getUsername();
                        users.password = item.getPassword();
                        users.bundleId = item.getPkg();
                        users.begintime = item.getBegintime();
                        users.regtime = item.getRegtime();
                        users.endtime = item.getEndtime();
                        UsersList.add(users);
                        response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                        response.status = "OK";
                    }
                }else if(regtime.equals(three)){
                    Date dateThree = DateUtil.getThreeMONTH(beginDate);    //获取后三个月日期
                    String threeMonth = DateUtil.TimeToString(dateThree);
                    endtime = threeMonth;
                    int rows = vpnUsersDao.updatePay(begintime,three,endtime,id);
                    if (rows > 0){
                        System.out.println("用户 "+ pkg + " "+id + " 付费 "+three+ " 月");
                    }
                    List<VpnUsers> vpnUsers =  vpnUsersDao.findById(id);  // 根据id查找用户信息
                    for(VpnUsers item : vpnUsers) {
                        List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                        VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                        users.id = item.getId();
                        users.username = item.getUsername();
                        users.password = item.getPassword();
                        users.bundleId = item.getPkg();
                        users.begintime = String.valueOf(item.getBegintime());
                        users.regtime = item.getRegtime();
                        users.endtime = String.valueOf(item.getEndtime());
                        UsersList.add(users);
                        response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                        response.status = "OK";
                    }
                }else if(regtime.equals(six)) {
                    Date dateSix = DateUtil.getSixMONTH(beginDate);     //获取后六个月日期
                    String sixMonth = DateUtil.TimeToString(dateSix);
                    endtime = sixMonth;
                    int rows = vpnUsersDao.updatePay(begintime, six, endtime, id);
                    if (rows > 0) {
                        System.out.println("用户 "+ pkg + " " + id + " 付费 " + six + " 月");
                    }
                    List<VpnUsers> vpnUsers = vpnUsersDao.findById(id);  // 根据id查找用户信息
                    for(VpnUsers item : vpnUsers) {
                        List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                        VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                        users.id = item.getId();
                        users.username = item.getUsername();
                        users.password = item.getPassword();
                        users.bundleId = item.getPkg();
                        users.begintime = String.valueOf(item.getBegintime());
                        users.regtime = item.getRegtime();
                        users.endtime = String.valueOf(item.getEndtime());
                        UsersList.add(users);
                        response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                        response.status = "OK";
                    }
                }else if(regtime.equals(twelve)){
                    Date dateTwelve = DateUtil.getTwelveMONTH(beginDate);  //获取后12个月日期
                    String twelveMonth = DateUtil.TimeToString(dateTwelve);
                    endtime = twelveMonth;
                    int rows = vpnUsersDao.updatePay(begintime, twelve, endtime, id);
                    if (rows > 0) {
                        System.out.println("用户 "+ pkg + " " + id + " 付费 " + twelve + " 月");
                    }
                    List<VpnUsers> vpnUsers = vpnUsersDao.findById(id);  // 根据id查找用户信息
                    for(VpnUsers item : vpnUsers) {
                        List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                        VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                        users.id = item.getId();
                        users.username = item.getUsername();
                        users.password = item.getPassword();
                        users.bundleId = item.getPkg();
                        users.begintime = String.valueOf(item.getBegintime());
                        users.regtime = item.getRegtime();
                        users.endtime = String.valueOf(item.getEndtime());
                        UsersList.add(users);
                        response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                        response.status = "OK";
                    }
                }else {
                    System.out.println("pay error");
                    response.status = "pay_error";
                }
            } catch (Exception e) {

            }
        }else {
            System.out.println("pay data error");
            response.status = "data_error";
        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " "+ pkg + "    End getVpnPay");
        return result;
    }*/

    /**
     * vpn 签到
     * */
    private ResponseBean getVpnSign(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getVpnSign");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnSignRequest req = request.getData(new VpnProto.VpnSignRequest());
        VpnProto.VpnSignResponse response = new VpnProto.VpnSignResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.userId).append("\t");
            sb.append(req.type).append("\t");
            sb.append(req.begintime);
            Log.vpnserver(sb.toString());
        }
        VpnSignDao vpnSignDao = (VpnSignDao) SpringHelper.getBean("dVpnSignDao");
        String pkg = req.bundleId;
        Long userId = req.userId;
        String begintime = req.begintime;
        String type = req.type;
        String endtime = null;
        try {
            if (!StringUtil.isBlank(pkg) && userId != null && !StringUtil.isBlank(begintime)) {
                Date beginDate = DateUtil.StringToTime(begintime);   //预留将接受来的字符类型签到时间转为日期类型
                if(type.toLowerCase().equals("sign")) {
                    List<VpnSign> signlist = vpnSignDao.findByPkgAndUserid(pkg, userId);
                    for (VpnSign sg : signlist) {
                        if(sg.getType().equals("sign")){    //原来签到过
                            String lasttime = sg.getEndtime();  //获取上一次这个用户签到时的 免费使用结束时间
                            lasttime = lasttime.substring(lasttime.lastIndexOf("-") + 1, lasttime.indexOf(" "));   //取得日期数
                            String thistime = begintime;    //获取本次这个用户签到时的 免费使用开始时间（签到时间）
                            thistime = thistime.substring(thistime.lastIndexOf("-") + 1, thistime.indexOf(" "));   //取得日期数
                            if (lasttime.equals(thistime)) {  //如果日期相同 则证明该用户为深夜签到 免费使用时间累加
                                Date endDate = DateUtil.StringToTime(lasttime);   //字符类型到期时间转为日期类型
                                Date dateone = DateUtil.getFourHour(endDate);   //获取后四个小时日期
                                String fourHour = DateUtil.TimeToString(dateone);
                                int rows = vpnSignDao.updateByPkgUserid(sg.getBegintime(),"sign" , fourHour, pkg, userId);
                                if (rows > 0) {
                                    System.out.println("用户 " + pkg + " " + userId + " 深夜签到，时间累加");
                                }
                                List<VpnProto.VpnSign> signsList = new ArrayList<>();
                                VpnProto.VpnSign signs = new VpnProto.VpnSign();
                                signs.id = 0;
                                signs.bundleId = pkg;
                                signs.userId = userId;
                                signs.begintime = begintime;
                                signs.type = "sign";
                                signs.endtime = fourHour;
                                signsList.add(signs);
                                response.vpnSignList = signsList.toArray(new VpnProto.VpnSign[0]);
                                response.status = "OK";
                            } else {   //非深夜隔天签到 时间并不累加 重新计算
                                Date dateone = DateUtil.getFourHour(beginDate);   //获取后四个小时日期
                                String fourHour = DateUtil.TimeToString(dateone);
                                int rows = vpnSignDao.updateByPkgUserid(begintime,"sign" , fourHour, pkg, userId);
                                if (rows > 0) {
                                    System.out.println("用户 " + pkg + " " + userId + " 非深夜隔天签到，时间重新计算");
                                }
                                List<VpnProto.VpnSign> signsList = new ArrayList<>();
                                VpnProto.VpnSign signs = new VpnProto.VpnSign();
                                signs.id = 0;
                                signs.bundleId = pkg;
                                signs.userId = userId;
                                signs.begintime = begintime;
                                signs.type = "sign";
                                signs.endtime = fourHour;
                                signsList.add(signs);
                                response.vpnSignList = signsList.toArray(new VpnProto.VpnSign[0]);
                                response.status = "OK";
                            }
                        }else {    //  第一次签到
                            Date dtf = DateUtil.getFourHour(beginDate);   //获取后四个小时日期
                            String fourHour = DateUtil.TimeToString(dtf);
                            int rows = vpnSignDao.updateByPkgUserid(begintime,"sign" ,fourHour, pkg, userId);   //更新用户信息，覆盖试用时间
                            if (rows > 0) {
                                System.out.println("用户 " + pkg + " " + userId + " 第一次签到");
                            }
                            List<VpnProto.VpnSign> signsList = new ArrayList<>();
                            VpnProto.VpnSign signs = new VpnProto.VpnSign();
                            signs.id = 0;
                            signs.bundleId = pkg;
                            signs.userId = userId;
                            signs.begintime = begintime;
                            signs.type = "sign";
                            signs.endtime = fourHour;
                            signsList.add(signs);
                            response.vpnSignList = signsList.toArray(new VpnProto.VpnSign[0]);
                            response.status = "OK";
                        }
                    }
                }else if(type.toLowerCase().equals("try")){  //  此处判断用户是否试用期
                    List<VpnSign> signlist = vpnSignDao.findByPkgAndUserid(pkg, userId);
                    if (signlist.size() > 0) {
                        List<VpnProto.VpnSign> tryList = new ArrayList<>();
                        for(VpnSign vp : signlist){
                            VpnProto.VpnSign signs = new VpnProto.VpnSign();
                            signs.id = vp.getId();
                            signs.bundleId = vp.getPkg();
                            signs.userId = vp.getUserId();
                            signs.begintime = vp.getBegintime();
                            signs.type = "try";
                            signs.endtime = vp.getEndtime();
                            tryList.add(signs);
                        }
                        response.vpnSignList = tryList.toArray(new VpnProto.VpnSign[0]);
                        response.status = "OK";
                    }else {
                        //新用户
                        Date dateone = DateUtil.getThreeDay(beginDate);   //获取后三天日期
                        String ThreeDay = DateUtil.TimeToString(dateone);
                        endtime = ThreeDay;
                        VpnSign vp = new VpnSign();
                        vp.setPkg(pkg);
                        vp.setUserId(userId);
                        vp.setBegintime(begintime);
                        vp.setType("try");
                        vp.setEndtime(endtime);
                        vp.setStatus(0);
                        int rows = vpnSignDao.insert(vp);    //将试用用户插入数据库
                        if (rows > 0) {
                            System.out.println("试用用户  " + pkg + " " + userId);
                        }
                        List<VpnProto.VpnSign> signsList = new ArrayList<>();
                        VpnProto.VpnSign signs = new VpnProto.VpnSign();
                        signs.id = 0;
                        signs.bundleId = pkg;
                        signs.userId = userId;
                        signs.begintime = begintime;
                        signs.type = "try";
                        signs.endtime = endtime;
                        signsList.add(signs);
                        response.vpnSignList = signsList.toArray(new VpnProto.VpnSign[0]);
                        response.status = "OK";
                    }
                }
            }else {
            System.out.println("sign data error");
            response.status = "data_error";
        }
        }catch (Exception e) {

        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " "+ pkg + "    End getVpnSign");
        return result;
    }

    /**
     * vpn 回返事件
     * */
    private ResponseBean getVpnEvent(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getVpnEvent");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnEventRequest req = request.getData(new VpnProto.VpnEventRequest());
        VpnProto.VpnEventResponse response = new VpnProto.VpnEventResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.userId);
            Log.vpnserver(sb.toString());
        }
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        VpnSignDao vpnSignDao = (VpnSignDao) SpringHelper.getBean("dVpnSignDao");
        VpnPurchaseDao VpnPurchaseDao = (VpnPurchaseDao) SpringHelper.getBean("dVpnPurchaseDao");
        String pkg = req.bundleId;
        Long userId = req.userId;
        try {
            if (!StringUtil.isBlank(pkg) && userId != null ) {
                List<VpnUsers> userlist = vpnUsersDao.findById(userId);
                List<VpnSign> signlist = vpnSignDao.findByPkgAndUserid(pkg,userId);
                List<VpnPurchase> purchaselist = VpnPurchaseDao.findByPkgAndUserid(pkg,userId);
                List<VpnProto.VpnUsers> UsersList = new ArrayList<>();
                List<VpnProto.VpnSign> signList = new ArrayList<>();
                List<VpnProto.VpnVerify> verifyList = new ArrayList<>();
                for(VpnUsers item : userlist){
                    VpnProto.VpnUsers users = new VpnProto.VpnUsers();
                    users.id = item.getId();
                    users.username = item.getUsername();
                    users.password = item.getPassword();
                    users.bundleId = item.getPkg();
                    users.regtime = item.getRegtime();
                    UsersList.add(users);
                }
                for(VpnSign item : signlist){
                    VpnProto.VpnSign users = new VpnProto.VpnSign();
                    users.id = item.getId();
                    users.bundleId = item.getPkg();
                    users.userId = item.getUserId();
                    users.begintime = item.getBegintime();
                    users.type = item.getType();
                    users.endtime = item.getEndtime();
                    signList.add(users);
                }
                if (purchaselist.size() > 0){
                    for(VpnPurchase item : purchaselist){
                        VpnProto.VpnVerify verify = new VpnProto.VpnVerify();
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
                    VpnProto.VpnVerify verify = new VpnProto.VpnVerify();
                    verify.bundleId = pkg;
                    verify.userId = userId;
                    verify.quantity = "Invalid";
                    verify.productId = "Invalid";
                    verify.purchaseDate = "Invalid";
                    verify.expiresDate = "Invalid";
                    verify.md5Receipt = "Invalid";
                    verifyList.add(verify);
                }
                response.vpnUsersList = UsersList.toArray(new VpnProto.VpnUsers[0]);
                response.vpnSignList = signList.toArray(new VpnProto.VpnSign[0]);
                response.vpnVerifyList = verifyList.toArray(new VpnProto.VpnVerify[0]);
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
        System.out.println(time + " "+ pkg + "    End getVpnEvent");
        return result;
    }

    /**
     * vpn 回返apple id
     * */
    private ResponseBean getVpnAppleId(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Start getVpnAppleId");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnAppleIdRequest req = request.getData(new VpnProto.VpnAppleIdRequest());
        VpnProto.VpnAppleIdResponse response = new VpnProto.VpnAppleIdResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.idfa).append("\t");
            sb.append(req.bundleId);
            Log.vpnserver(sb.toString());
        }
        VpnAppleIdDao vpnAppleIdDao = (VpnAppleIdDao) SpringHelper.getBean("dVpnAppleIdDao");
        String pkg = req.bundleId;
        try {
            if (!StringUtil.isBlank(pkg)) {
                List<VpnAppleId> applelist = vpnAppleIdDao.findByPkg(pkg);
                List<VpnProto.VpnAppleId> AppleIdList = new ArrayList<>();
                for(VpnAppleId item : applelist){
                    VpnProto.VpnAppleId users = new VpnProto.VpnAppleId();
                    users.id = item.getId();
                    users.bundleId = item.getPkg();
                    users.appleid = item.getAppleid();
                    AppleIdList.add(users);
                }
                response.vpnAppleIdList = AppleIdList.toArray(new VpnProto.VpnAppleId[0]);
                System.out.println("用户 "+pkg+ "  appleid success");
                response.status = "OK";
            }else {
                System.out.println("appleid data error");
                response.status = "data_error";
            }
        }catch (Exception e) {

        }
        response.time = System.currentTimeMillis();
        result.setData(response);
        System.out.println(time + " "+ pkg + "    End getVpnAppleId");
        return result;
    }

    /**
     * vpn iap二次验证
     * */
    private ResponseBean getIOSVerify(RequestBean request) {
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
        System.out.println(time + "    Start getIOSVerify");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnIapVerifyRequest req = request.getData(new VpnProto.VpnIapVerifyRequest());
        VpnProto.VpnIapVerifyResponse response = new VpnProto.VpnIapVerifyResponse();

        if (req != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time).append("\t");
            sb.append(req.geo).append("\t");
            sb.append(req.bundleId).append("\t");
            sb.append(req.userId).append("\t");
            sb.append(req.voucher).append("\t");
            sb.append(req.regtime);
            Log.vpnserver(sb.toString());
        }
        VpnPurchaseDao vpnPurchaseDao = (VpnPurchaseDao) SpringHelper.getBean("dVpnPurchaseDao");
        VpnUsersDao vpnUsersDao = (VpnUsersDao) SpringHelper.getBean("dVpnUsersDao");
        String pkg = req.bundleId;
        Long userid = req.userId;
        String regtime = req.regtime;
        //苹果客户端传上来的收据,是最原据的收据
        String receipt = req.voucher;
        System.out.println("来自苹果端的验证...");
        String password ="f9dc3337f05744e9815a16a660cc0376";
        System.out.println("receipt  "+receipt);
        if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(userid) && !StringUtil.isBlank(receipt)) {
            //拿到收据的MD5
            String md5_receipt = Md5.md5(receipt);
            //默认是无效账单
            String answer = null;
            answer = BuyUtil.STATE_C + "#" + md5_receipt;
            //查询数据库，看是否是己经验证过的账号
            List<VpnPurchase> pulist = vpnPurchaseDao.findByMd5Receipt(md5_receipt);  //查库
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
                        //将注册时长保存到vpn_user表
                        int nums = vpnUsersDao.updateRegtime(regtime ,userid);
                        if (nums > 0) {
                            System.out.println("用户 "+ pkg + " " + userid+ " 付费 " + regtime + " 月");
                        }
                        //下架vpn_purchase表中过期数据
                        int count = vpnPurchaseDao.updateStByUseridAndPkg(pkg,userid);
                        if (count > 0) {
                            System.out.println("下架用户过期付费信息  "+ pkg + "  " + userid);
                        }
                        //将苹果服务器验证信息保存到vpn_purchase表
                        VpnPurchase vpnPurchase = new VpnPurchase();
                        vpnPurchase.setPkg(pkg);
                        vpnPurchase.setUser_id(userid);
                        vpnPurchase.setQuantity(quantity);
                        vpnPurchase.setProduct_id(product_id);
                        vpnPurchase.setPurchase_date(purchase_date);
                        vpnPurchase.setExpires_date(expires_date);
                        vpnPurchase.setMd5_receipt(md5_receipt);
                        vpnPurchase.setStatus(0);
                        int rows = vpnPurchaseDao.insert(vpnPurchase);
                        if (rows > 0) {
                            System.out.println("验证结果 " + pkg + " answer " + answer + "  IOSVerify success");
                            List<VpnProto.VpnVerify> VpnVerifyList = new ArrayList<>();
                            VpnProto.VpnVerify verify = new VpnProto.VpnVerify();
                            verify.userId = userid;
                            verify.bundleId = pkg;
                            verify.quantity = quantity;
                            verify.productId = product_id;
                            verify.purchaseDate = purchase_date;
                            verify.expiresDate = expires_date;
                            verify.md5Receipt = md5_receipt;
                            VpnVerifyList.add(verify);
                            response.vpnVerifyList = VpnVerifyList.toArray(new VpnProto.VpnVerify[0]);
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
                            //将注册时长保存到vpn_user表
                            int nums = vpnUsersDao.updateRegtime(regtime ,userid);
                            if (nums > 0) {
                                System.out.println("用户 "+ pkg + " " + userid+ " 付费 " + regtime + " 月");
                            }
                            //下架vpn_purchase表中过期数据
                            int count = vpnPurchaseDao.updateStByUseridAndPkg(pkg,userid);
                            if (count > 0) {
                                System.out.println("下架用户过期付费信息  "+ pkg + "  " + userid);
                            }
                            //将苹果服务器最新验证信息保存到vpn_purchase表
                            VpnPurchase vpnPurchase = new VpnPurchase();
                            vpnPurchase.setPkg(pkg);
                            vpnPurchase.setUser_id(userid);
                            vpnPurchase.setQuantity(quantity);
                            vpnPurchase.setProduct_id(product_id);
                            vpnPurchase.setPurchase_date(purchase_date);
                            vpnPurchase.setExpires_date(expires_date);
                            vpnPurchase.setMd5_receipt(md5_receipt);
                            vpnPurchase.setStatus(0);
                            int rows = vpnPurchaseDao.insert(vpnPurchase);
                            if (rows > 0) {
                                System.out.println("验证结果 " + pkg + " answer " + answer + "  IOSVerify success");
                                List<VpnProto.VpnVerify> VpnVerifyList = new ArrayList<>();
                                VpnProto.VpnVerify verify = new VpnProto.VpnVerify();
                                verify.userId = userid;
                                verify.bundleId = pkg;
                                verify.quantity = quantity;
                                verify.productId = product_id;
                                verify.purchaseDate = purchase_date;
                                verify.expiresDate = expires_date;
                                verify.md5Receipt = md5_receipt;
                                VpnVerifyList.add(verify);
                                response.vpnVerifyList = VpnVerifyList.toArray(new VpnProto.VpnVerify[0]);
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
        System.out.println(time + " "+ pkg + "    End getIOSVerify");
        return result;
    }

    /**
     * 广告下发策略接口 by Ryan
     * */
    private ResponseBean getVpnStrategy(RequestBean request){
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time+ "    Start getVpnStrategy");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnStrategyRequest req = request.getData(new VpnProto.VpnStrategyRequest());
        VpnProto.VpnStrategyResponse response = new VpnProto.VpnStrategyResponse();

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
            Log.vpnserver(sb.toString());

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
            List<VpnProto.AdControl> adControlList = new ArrayList<>();
            List<VpnProto.AdStrategy> adStrategyList = new ArrayList<>();
            List<VpnProto.AdClick> adClicksList = new ArrayList<>();
            List<VpnProto.AdResources> adResourcesList = new ArrayList<>();

            if(!StringUtil.isBlank(geo)){
                List<AdControl> adControls = adControlDao.findByPkgAndContry(pkg,geo);
                List<AdStrategy> adStrategies = adStrategyDao.findByPkgAndCountry(pkg,geo);
                List<AdClick> adClicks = adClickDao.findByPkgAndCountry(pkg,geo);
                List<AdResources> adResources = adResourcesDao.findByPkgAndCountry(pkg,geo);

                for(AdControl item : adControls){
                    VpnProto.AdControl control = new VpnProto.AdControl();
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
                    VpnProto.AdStrategy adStrategy = new VpnProto.AdStrategy();
                    adStrategy.adType = item.getAdType();
                    adStrategy.adSource = item.getAdSource();
                    adStrategy.adID = item.getAdId();
                    adStrategy.positionID = item.getPosition();
                    adStrategy.priority = item.getPriority();
                    adStrategy.adreward = item.getAdreward();
                    adStrategyList.add(adStrategy);
                }
                for (AdClick item : adClicks){
                    VpnProto.AdClick click = new VpnProto.AdClick();
                    click.positionID = item.getPosition();
                    click.minClick = item.getMinClick();
                    click.maxClick = item.getMaxClick();
                    adClicksList.add(click);
                }
                /*
                修改此处千万看清楚
                }*/
                for (AdResources ad : adResources){
                    VpnProto.AdResources adResources1 = new VpnProto.AdResources();
                    adResources1.soundKey = ad.getSourcekey();  //music版本中 为soundKey   维持上线版本 两个key同时启用  ，为后期维护  其他应用数据源表以后统一只有sourcekey
                    adResources1.jamKey = ad.getTypekey();      //music版本中 为jamKey  其他发行软件中均表示key的类型
                    adResources1.type = ad.getType();   //表示使用key的类型
                    adResourcesList.add(adResources1);
                }
            }

            if(adControlList.size() <= 0){
                List<AdControl> allAdcontrol = adControlDao.findByPkgAndContry(pkg,"ALL");
                for(AdControl item : allAdcontrol){
                    VpnProto.AdControl control = new VpnProto.AdControl();
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
                    VpnProto.AdStrategy adStrategy = new VpnProto.AdStrategy();
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
                    VpnProto.AdClick click = new VpnProto.AdClick();
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
                    VpnProto.AdResources resources = new VpnProto.AdResources();
                    resources.soundKey = item.getSourcekey();
                    resources.jamKey = item.getTypekey();
                    resources.type = item.getType();
                    adResourcesList.add(resources);
                }
            }
            response.adControlList = adControlList.toArray(new VpnProto.AdControl[0]);
            response.adStrategyList = adStrategyList.toArray(new VpnProto.AdStrategy[0]);
            response.adClickList = adClicksList.toArray(new VpnProto.AdClick[0]);
            response.adResourcesList = adResourcesList.toArray(new VpnProto.AdResources[0]);
        }

        response.serverTime = System.currentTimeMillis();

        if(response == null){
            response = new VpnProto.VpnStrategyResponse();
        }
        result.setData(response);
        System.out.println(time+ "    End getVpnStrategy");
        return result;
    }

    /**
     * 数据统计接口 by Ryan
     * */
    private ResponseBean getVpnData(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getVpnData");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnDataRequest req = request.getData(new VpnProto.VpnDataRequest());
        VpnProto.VpnDataResponse response = new VpnProto.VpnDataResponse();

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
                for(VpnProto.eventApplicationStartup item : req.applicationStartupList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeStartup).append("\t");
                }
                sb.append("eventPageSwitching").append("\t");
                for(VpnProto.eventPageSwitching item : req.pageSwitchingList){
                    sb.append(item.geo).append("\t");
                    sb.append(item.pageName).append("\t");
                    sb.append(item.timeSwitching).append("\t");
                }
                sb.append("eventAdRequest").append("\t");
                for(VpnProto.eventAdRequest item : req.adRequestList) {
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeRequest).append("\t");
                }
                sb.append("eventAdShow").append("\t");
                for(VpnProto.eventAdShow item : req.adShowList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeShow).append("\t");
                }
                sb.append("eventAdClick").append("\t");
                for(VpnProto.eventAdClick item : req.adClickList){
                    sb.append(item.adPositionNumber).append("\t");
                    sb.append(item.adType).append("\t");
                    sb.append(item.geo).append("\t");
                    sb.append(item.timeClick);
                }
                Log.vpnserver(sb.toString());

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
        System.out.println(time + "    End getVpnData");
        result.setData(response);
        return result;

    }

    /**
     * 服务器端推送接口 by Ryan
     * */
    private ResponseBean getVpnPush(RequestBean request) {
        String time = null;
        try {
            time = DateUtil.DateToString(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(time + "    Begin getVpnPush");
        ResponseBean result = new ResponseBean(1);
        VpnProto.VpnPushRequest req = request.getData(new VpnProto.VpnPushRequest());
        VpnProto.VpnPushResponse response = new VpnProto.VpnPushResponse();

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
                Log.vpnserver(sb.toString());
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
        System.out.println(time + "    End getVpnPush");
        return result;
    }

}
