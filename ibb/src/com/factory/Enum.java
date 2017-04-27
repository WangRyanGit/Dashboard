package com.factory;

public class Enum
{
    public final static int    NETWORK_UNKOWN      = 0;
    public final static int    NETWORK_WIFI        = 1;
    public final static int    NETWORK_2G          = 2;
    public final static int    NETWORK_3G          = 3;
    public final static int    NETWORK_LTE         = 4;

    public final static int    AD_APK              = 1;                 // apk下载广告
    public final static int    AD_YEAHMOBI         = 2;                 // yeahmobi模拟googleplay广告
    public final static int    AD_AFFLIATE         = 3;                 // 联盟广告
    public final static int    AD_GOOGLEPLAY       = 4;                 // google
    // play广告
    public final static int    AD_GOOGLEPLAY_QUICK = 5;                 // google
    // play快速下载广告
    public final static int    AD_APK_TRACK        = 6;                 // 自己提供apk的方式，但是需要点击track的地址来反馈的
    public final static int    AD_APK_TAPJOY       = 7;
    public final static int    AD_APK_TRACKCLICK   = 8;                 // 需要手机端于服务器比对获取交集。特点：track地址变化。Tapjoy,Applift
    // 广告

    // postback_log
    public final static String POSTBACK_LOG        = "allpostcallback;";

    // statistype
    public final static int    STATISREQ           = 1;                 // 展示
    public final static int    STATISCLICK         = 2;                 // 点击
    public final static int    STATISDOWN          = 3;                 // 下载
    public final static int    STATISINSTALL       = 4;                 // 安装

    public static final int    BATCH_NUM           = 1000;               // 批量插入的数量

}
