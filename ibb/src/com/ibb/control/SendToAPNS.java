package com.ibb.control;

import com.ibb.bean.AdTokens;
import com.ibb.dao.AdTokensDao;
import com.ibb.mail.JavaMailWithAttachment;
import com.ibb.mail.ReadProperties;
import com.ibb.util.DateUtil;
import com.ibb.util.StringUtil;
import com.util.SpringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ryan on 2017/3/20.
 */
public class SendToAPNS {
    //定义默认路径
    private static String paths = "/data/tools/sendpush/";

    public void sendMusicZheng(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.listenmusicsoho";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        //tokens.add("66455e8534c8aac25c1e9693143b08702a1644bf64b08ba11685c69f04ee1c86");
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "music/zhengyong/zypushDis.p12";     //上线正式用
        //String path = paths + "music/zhengyong/zypushDev.p12";      //上线测试用
        //String path = "D:\IOS后端内容\iospush\music\zhengyong\zypushDev.p12";   //本地测试用
        String password="123456";
        String message="{'aps':{'alert':'Music without borders, you and I share'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);
    }

    public void sendMusicHan() {
        MainSend send = new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.music.listen";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens = new ArrayList<String>();
        //tokens.add("66455e8534c8aac25c1e9693143b08702a1644bf64b08ba11685c69f04ee1c86");
        for (AdTokens ad : tokenlist) {
            if (ad.getPkg().equals(pkg)) {
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "music/hanyibo/hybpushDis.p12";     //上线正式用
        //String path = paths + "music/hanyibo/hybpushDev.p12";      //上线测试用
        //String path = "D:\IOS后端内容\iospush\music\hanyibo\hybpushDev.p12";   //本地测试用
        String password = "123456";
        String message = "{'aps':{'alert':'Music without borders, you and I share'}}";
        Integer count = 1;
        boolean sendCount = false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);
    }
    public void sendMusicLiu() {
        MainSend send = new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.FreeMusic20170213";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens = new ArrayList<String>();
        //tokens.add("66455e8534c8aac25c1e9693143b08702a1644bf64b08ba11685c69f04ee1c86");
        for (AdTokens ad : tokenlist) {
            if (ad.getPkg().equals(pkg)) {
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "music/liuzongxin/LZX2_push_distribution.p12";     //上线正式用
        //String path = paths + "music/liuzongxin/LZX2_push_developer.p12";      //上线测试用
        //String path = "D:\IOS后端内容\iospush\music\liuzongxin\LZX2_push_developer.p12";   //本地测试用
        String password = "123456";
        String message = "{'aps':{'alert':'Music without borders, you and I share'}}";
        Integer count = 1;
        boolean sendCount = false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);
    }

    public void sendMusicShang() {
        MainSend send = new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.onlinemusic.player";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens = new ArrayList<String>();
        //tokens.add("66455e8534c8aac25c1e9693143b08702a1644bf64b08ba11685c69f04ee1c86");
        for (AdTokens ad : tokenlist) {
            if (ad.getPkg().equals(pkg)) {
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "music/shangxiuqiao/sxq2_push_distribution.p12";     //上线正式用
        //String path = paths + "music/shangxiuqiao/sxq2_push_development.p12";      //上线测试用
        //String path = "D:\IOS后端内容\iospush\music\shangxiuqiao\sxq2_push_development.p12";   //本地测试用
        String password = "123456";
        String message = "{'aps':{'alert':'Music without borders, you and I share'}}";
        Integer count = 1;
        boolean sendCount = false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);
    }


    public void sendYouTubeHan(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.YouTubePro";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        System.out.println("tokenlist.size  "+tokenlist.size());
        List<String> tokens= new ArrayList<String>();
        //tokens.add("66455e8534c8aac25c1e9693143b08702a1644bf64b08ba11685c69f04ee1c86");
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                System.out.println("token  "+ad.getTokens());
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "youtube/hanyibo/TubePro_distribution.p12";     //上线正式用
        //String path = paths + "youtube/hanyibo/TubePro_development.p12";      //上线测试用
        //String path = "E:\\TubePro_distribution.p12";   //本地测试用
        String password="";
        String message="{'aps':{'alert':'Music without borders, you and I share'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);
    }

    public void sendYouTubeZheng(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.myvideotube";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        //tokens.add("66455e8534c8aac25c1e9693143b08702a1644bf64b08ba11685c69f04ee1c86");
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "youtube/zhengyong/zhengyongshangxiantuisongzhengshu.p12";     //上线正式用
        //String path = paths + "youtube/zhengyong/zhengyongshangxiantuisongzhengshu.p12";      //上线测试用
        //String path = "D:\IOS后端内容\iospush\YouTube\zhengyong\zhengyongceshituisongzhengshu.p12";   //本地测试用
        String password="";
        String message="{'aps':{'alert':'Music without borders, you and I share'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);
    }

    public void sendFollowZhou(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.shadowfollow.com";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "follow/zhouliang/ShadowReport_push.p12";     //上线正式用
        //String path = paths + "follow/zhouliang/ShadowReport_devPush.p12";      //上线测试用
        //String path = "D:\\IOS后端内容\\iospush\\follow\\zhouliang\\ShadowReport_devPush.p12";   //本地测试用
        String password="shadowreport123";
        String message="{'aps':{'alert':'fast 8'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount, pkg);
    }

    public void sendFollowLiu(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.CircleReport.app";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "follow/liuzongxin/CircleReport_push.p12";     //上线正式用
        //String path = paths + "follow/liuzongxin/CircleReport_push.p12";      //上线测试用
        //String path = "D:\\IOS后端内容\\iospush\\follow\\liuzongxin\\CircleReport_Dev.p12";   //本地测试用
        String password="1234567890";
        String message="{'aps':{'alert':'fast 8'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount, pkg);
    }

    public void sendFollowShang(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.followersLife.app";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        tokens.add("");
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "follow/shangxiuqiao/FollowersLife_push.p12";     //上线正式用
        //String path = paths + "follow/shangxiuqiao/FollowersLife_push.p12";      //上线测试用
        //String path = "D:\\IOS后端内容\\iospush\\follow\\shangxiuqiao\\FollowersLife_dev.p12";   //本地测试用
        String password="1234567890";
        String message="{'aps':{'alert':'fast 8'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount, pkg);
    }

    public void sendFollowZheng(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.follow.app";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        //tokens.add("b40897f7ee0507b1bb0fc05cebff43c459671a930bd49bc9df92aa8b24e8b4cb");
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "follow/zhengyong/FollowersReport_push.p12";     //上线正式用
        //String path = paths + "follow/zhengyong/FollowersReport_push.p12";      //上线测试用
        //String path = "D:\\IOS后端内容\\iospush\\follow\\zhengyong\\FollowersReport_Dev.p12";   //本地测试用
        String password="1234567890";
        String message="{'aps':{'alert':'fast 8'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount, pkg);
    }

    public void sendVpnZheng(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.vpnActive.app";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "vpn/zhengyong/activeVPN_push.p12";     //上线正式用
        //String path = paths + "vpn/zhengyong/activeVPN_dev.p12";      //上线测试用
        //String path = "D:\IOS后端内容\iospush\vpn\zhengyong\activeVPN_dev.p12";   //本地测试用
        String password="activevpn123";
        String message="{'aps':{'alert':'Out of Asia, embrace the world'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount, pkg);
    }

    public void sendColorZheng(){
        MainSend send=new MainSend();
        AdTokensDao adtokensdao = (AdTokensDao) SpringHelper.getBean("dAdTokensDao");
        String pkg = "com.This-colour";
        List<AdTokens> tokenlist = adtokensdao.findByPkg(pkg);
        List<String> tokens= new ArrayList<String>();
        //tokens.add("64005fe35b3ded34960b688f18b500efcac30cb0bfebd0c122a67c500887678b");
        for(AdTokens ad : tokenlist) {
            if(ad.getPkg().equals(pkg)){
                tokens.add(ad.getTokens().toString());
            }
        }
        String path = paths + "color/zhengyong/color.ZYdis.p12";     //上线正式用
        //String path = paths + "color/zhengyong/color.ZYdev.p12";      //上线测试用
        //String path = "D:\\IOS后端内容\\iospush\\color\\zhengyong\\color.ZYdev.p12";   //本地测试用
        String password="color123";
        String message="{'aps':{'alert':'Out of Asia, embrace the world'}}";
        Integer count=1;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount, pkg);
    }

    public void send() {
        System.out.println("Ready to send mail ...");
        JavaMailWithAttachment se = new JavaMailWithAttachment(false);
        StringBuilder builder = new StringBuilder();
        StringBuffer url = new StringBuffer();
        String user = "Pony";
        // type = forget 密码重置
        String verifyCode = StringUtil.generateVerifyCode();    //验证码
        url.append("<font color='red' size: 16px>" + verifyCode + "</font>");
        // 正文
        builder.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body><div style=style=\"width: 90%\">");
        builder.append("<span><font color='green'>"+"Hello  "+ user + "</font></span>");
        builder.append("<br/><br/>");
        builder.append("<span>"+"We received a reset password request for your account.Please enter the following characters in the validation box to complete the reset password operation."+ "</span>");
        builder.append("<br/><br/>");
        builder.append("<span>"+"VerifyCode:   " + url + "</span>");
        builder.append("<br/><br/>");
        builder.append("<span>"+"If you did not make this request, please report this suspicious activity to alicexiong0707@gmail.com."+"</span>");
        builder.append("<br/><br/><br/>");
        builder.append("<span>"+"Regards,"+"</span><br/>");
        builder.append("<span>"+"ActiveVPN team"+"</span>");
        builder.append("</div></body></html>");
        String title = "ActiveVPN Reset Password";
        String email = "410183874@qq.com";
        se.doSendHtmlEmail(title, builder.toString(), email, null);
        System.out.println("sendMail end!");
    }

}
