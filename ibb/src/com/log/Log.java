package com.log;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.util.Util;

public class Log {
    protected static final Logger ADMIN_REQ_LOG = Logger.getLogger("admin");    // 管理后台
    protected static final Logger APPREG_LOG = Logger.getLogger("appreg");  //用户注册
    protected static final Logger ADSHOW_LOG = Logger.getLogger("adshow");  //广告策略
    protected static final Logger ADDATA_LOG = Logger.getLogger("addata");  //数据统计
    protected static final Logger PUSH_LOG = Logger.getLogger("sendpush");  //数据统计

    protected static final Logger VPN_SERVER = Logger.getLogger("vpnserver");  //vpn暂用

    protected static final Logger YOUTUBE_LOG = Logger.getLogger("youtube");  //youtube暂用

    protected static final Logger COLOR_LOG = Logger.getLogger("color");  //youtube暂用

    protected static final Logger FOLLOW_LOG = Logger.getLogger("follow");  //follow暂用

    protected static final Logger APPACTIVE_LOG = Logger.getLogger("appactive"); // 无法识别的手机短信中心号
    protected static final Logger AD_LOG = Logger.getLogger("ad");
    protected static final Logger USER_ERROR_LOG = Logger.getLogger("error");
    public static final Logger TEST = Logger.getLogger("TEST");

    private static final int BUFFER_SIZE = 1024;

    public final static int ERROR_LOG = 1;
    public final static int ADMIN_LOG = 2;
    public final static int CLIENT_LOG = 3;

    private static void errorLog(RequestBean request, ResponseBean response,
                                 String result, long totaltime) {
        StringBuffer sb = new StringBuffer(BUFFER_SIZE);
        sb.append(Util.getDateFormatString(new Date(), 4));
        sb.append("|");
        sb.append(request.getIp());
        sb.append("|");
        sb.append(request.getSrc());
        sb.append("|");
        sb.append(request.toString());
        sb.append("|");
        sb.append(response.toString());
        sb.append("|");
        sb.append(totaltime);
        sb.append("|");
        sb.append(result);
        USER_ERROR_LOG.error(sb.toString());
    }

    public static void log(Exception e) {
        USER_ERROR_LOG.error("error", e);
    }


    /**
     * 管理后台请求日志
     */
    private static void adminLog(RequestBean request, ResponseBean response,
                                 String result, long totaltime) {
        if (request == null) {
            return;
        }
        StringBuffer sb = new StringBuffer(BUFFER_SIZE);
        sb.append(Util.getDateFormatString(new Date(), 4));
        sb.append("|");
        sb.append(request.getIp());
        sb.append("|");
        sb.append(request.getSrc());
        sb.append("|");
        sb.append(totaltime);
        sb.append("|");
        sb.append(request.toString());
        sb.append("|");
        sb.append(response.toString());
        sb.append("|");
        sb.append(result);
        ADMIN_REQ_LOG.info(sb.toString());
    }

    public static void activeApp(String data) {
        if (data != null) {
            APPACTIVE_LOG.info(data);
        }
    }

    public static void regApp(String data) {
        if (data != null) {
            APPREG_LOG.info(data);
        }
    }

    public static void adShow(String data) {
        if (data != null) {
            ADSHOW_LOG.info(data);
        }
    }

    public static void adData(String data) {
        if (data != null) {
            ADDATA_LOG.info(data);
        }
    }

    public static void sendPush(String data){
        if(data != null){
            PUSH_LOG.info(data);
        }
    }
    public static void vpnserver(String data){
        if(data != null){
            VPN_SERVER.info(data);
        }
    }
    public static void YouTube(String data){
        if(data != null){
            YOUTUBE_LOG.info(data);
        }
    }
    public static void Color(String data){
        if(data != null){
            COLOR_LOG.info(data);
        }
    }
    public static void Follow(String data){
        if(data != null){
            FOLLOW_LOG.info(data);
        }
    }
    public static void adLog(String data) {
        if (data != null) {
            AD_LOG.info(data);
        }
    }

}
