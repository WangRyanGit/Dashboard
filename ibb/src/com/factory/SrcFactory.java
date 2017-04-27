package com.factory;

import javax.servlet.http.HttpServletRequest;

public class SrcFactory
{
    public final static int SRC_CHECKUP_LIB  = 1;
    public final static int SRC_GET_UPDATEINFO = 6;//获取升级信息
    public final static int SRC_GET_ADINFO = 7;//获取google广告 appkey，广告单元key
    public final static int SRC_GET_TIME = 8;
    /*
    * My Music 接口
     */
    public final static int SRC_GET_USERREGISTER = 10;  //用户注册接口
    public final static int SRC_GET_ADSHOW = 11;    //广告展示策略
    public final static int SRC_GET_DATAST = 12;    //数据统计接口
    public final static int SRC_SEND_PUSH = 13;     //服务器端推送
    public final static int SRC_GET_APPS = 14;     //推广自己产品
    /*
    * vpn 接口
    */
    public final static int SRC_VPN_SERVER = 20;     //vpn服务ip列表
    public final static int SRC_VPN_REGISTER = 21;     //vpn用户注册
    public final static int SRC_VPN_LOGIN = 22;     //vpn用户登录
    public final static int SRC_VPN_CHANGE = 23;     //vpn修改密码
    public final static int SRC_VPN_PAY = 24;     //付费
    public final static int SRC_VPN_SIGN = 25;     //签到
    public final static int SRC_VPN_EVENT = 26;     //事件回返
    public final static int SRC_VPN_CODE = 27;      //短信验证
    public final static int SRC_VPN_APPLEID = 28;      //appleid
    public final static int SRC_VPN_BUYVERIFY = 29;      //ios iap 验证
    public final static int SRC_VPN_STRATEGY = 30;    //广告展示策略
    public final static int SRC_VPN_DATA = 31;    //数据统计接口
    public final static int SRC_VPN_SENDPUSH = 32;     //服务器端推送

    /*
    * YouTube 接口
     */
    public final static int SRC_YOU_REGISTER = 35;  //用户注册接口
    public final static int SRC_YOU_STRATEGY = 36;    //广告展示策略
    public final static int SRC_YOU_DATA = 37;    //数据统计接口
    public final static int SRC_YOU_SENDPUSH = 38;     //服务器端推送

    /*
    * Color 接口
     */
    public final static int SRC_COLOR_PNG = 42;  //画图本图片源接口
    public final static int SRC_COLOR_REGISTER = 43;  //用户注册接口
    public final static int SRC_COLOR_STRATEGY = 44;    //广告展示策略
    public final static int SRC_COLOR_DATA = 45;    //数据统计接口
    public final static int SRC_COLOR_SENDPUSH = 46;     //服务器端推送

    /*
    * Follow 接口
     */
    public final static int SRC_FOLLOW_REGISTER = 50;  //用户注册接口
    public final static int SRC_FOLLOW_STRATEGY = 51;    //广告展示策略
    public final static int SRC_FOLLOW_DATA = 52;    //数据统计接口
    public final static int SRC_FOLLOW_SENDPUSH = 53;     //服务器端推送
    public final static int SRC_FOLLOW_IAP = 54;      //ios iap 验证
    public final static int SRC_FOLLOW_EVENT = 55;     //事件回返


    public final static int SRC_API_END      = 100;     //超过100失效

    /**
     * 根据请求的URL来判断来源
     * */
    public static int getSrc(final HttpServletRequest request)
    {
        String src = request.getRequestURI().substring(
                request.getContextPath().length());
/*        System.out.println("request.getRequestURI()   "+request.getRequestURI());
        System.out.println("request.getRequestURL()   "+request.getRequestURL());
        System.out.println("request.getContextPath()   "+request.getContextPath());*/
        if (src.contains("/api/1/"))
        {
            return SRC_CHECKUP_LIB;
        }
        else if (src.contains("/api/6"))
        {
            return SRC_GET_UPDATEINFO;
        }
        else if (src.contains("/api/7"))
        {
            return SRC_GET_ADINFO;
        }
        else if (src.contains("/api/8"))
        {
            return SRC_GET_TIME;
        }
        else if (src.contains("/api/10"))
        {
            return SRC_GET_USERREGISTER;
        }
        else if (src.contains("/api/11"))
        {
            return  SRC_GET_ADSHOW;
        }
        else if (src.contains("/api/12"))
        {
            return SRC_GET_DATAST;
        }
        else if (src.contains("/api/13"))
        {
            return SRC_SEND_PUSH;
        }
        else if (src.contains("/api/14"))
        {
            return SRC_GET_APPS;
        }
        else if (src.contains("/vpn/20"))
        {
            return SRC_VPN_SERVER;
        }
        else if (src.contains("/vpn/21"))
        {
            return SRC_VPN_REGISTER;
        }
        else if (src.contains("/vpn/22"))
        {
            return SRC_VPN_LOGIN;
        }
        else if (src.contains("/vpn/23"))
        {
            return SRC_VPN_CHANGE;
        }
        else if (src.contains("/vpn/24"))
        {
            return SRC_VPN_PAY;
        }
        else if (src.contains("/vpn/25"))
        {
            return SRC_VPN_SIGN;
        }
        else if(src.contains("/vpn/26"))
        {
            return SRC_VPN_EVENT;
        }
        else if(src.contains("/vpn/27"))
        {
            return SRC_VPN_CODE;
        }
        else if(src.contains("/vpn/28"))
        {
            return SRC_VPN_APPLEID;
        }
        else if (src.contains("/vpn/29"))
        {
            return SRC_VPN_BUYVERIFY;
        }
        else if (src.contains("/vpn/30"))
        {
            return SRC_VPN_STRATEGY;
        }
        else if (src.contains("/vpn/31"))
        {
            return SRC_VPN_DATA;
        }
        else if (src.contains("/vpn/32"))
        {
            return SRC_VPN_SENDPUSH;
        }
        else if(src.contains("/youtb/35"))
        {
            return SRC_YOU_REGISTER;
        }
        else if(src.contains("/youtb/36"))
        {
            return SRC_YOU_STRATEGY;
        }
        else if(src.contains("/youtb/37"))
        {
            return SRC_YOU_DATA;
        }
        else if(src.contains("/youtb/38"))
        {
            return SRC_YOU_SENDPUSH;
        }
        else if(src.contains("/color/42"))
        {
            return SRC_COLOR_PNG;
        }
        else if(src.contains("/color/43"))
        {
            return SRC_COLOR_REGISTER;
        }
        else if(src.contains("/color/44"))
        {
            return SRC_COLOR_STRATEGY;
        }
        else if(src.contains("/color/45"))
        {
            return SRC_COLOR_DATA;
        }
        else if(src.contains("/color/46"))
        {
            return SRC_COLOR_SENDPUSH;
        }
        else if(src.contains("/follow/50"))
        {
            return SRC_FOLLOW_REGISTER;
        }
        else if(src.contains("/follow/51"))
        {
            return SRC_FOLLOW_STRATEGY;
        }
        else if(src.contains("/follow/52"))
        {
            return SRC_FOLLOW_DATA;
        }
        else if(src.contains("/follow/53"))
        {
            return SRC_FOLLOW_SENDPUSH;
        }
        else if(src.contains("/follow/54"))
        {
            return SRC_FOLLOW_IAP;
        }
        else if(src.contains("/follow/55"))
        {
            return SRC_FOLLOW_EVENT;
        }
        else
        {
            return SRC_API_END;
        }
    }
}
