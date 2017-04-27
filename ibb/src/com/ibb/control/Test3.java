package com.ibb.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibb.bean.*;
import com.ibb.dao.*;
import com.ibb.util.DateUtil;
import com.util.Md5;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Ryan on 2017/3/9.
 */
public class Test3 {

    private static String generateVerifyCode() {
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }
    public static void main(String[] args) {
        try {
            String re = generateVerifyCode();
            re = re.toLowerCase();
            System.out.print("验证码 " + re);
        } catch (Exception e) {
            e.printStackTrace();
        }



        /*try{
            String begintime = "2017-04-01 16:03";
            Date beginDate = null;   //预留将接受来的字符类型签到时间转为日期类型
            *//*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM");
            dateFormat.parse(begintime);*//*
            beginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(begintime);
            Date dateone = DateUtil.getFourHour(beginDate);   //获取后四个小时日期
            String fourHour = DateUtil.TimeToString(dateone);
            String endtime = fourHour;
            System.out.println(endtime + "  endtime");

            Date dateone1 = DateUtil.getThreeDay(beginDate);   //获取后三天日期
            String fourHour1 = DateUtil.TimeToString(dateone1);
            endtime = fourHour1;
            System.out.println(endtime + "  endtime");

            Date date = new Date();
            String time = DateUtil.TimeToString(date);
            System.out.println(time);

            String time1 = DateUtil.DateToString(new Date());
            System.out.println(time1);

            Date date2 = DateUtil.StringToTime("2017-10-20 12:22");
            System.out.println(date2);

        }catch (Exception e){
            e.printStackTrace();
        }*/





        // 读取server.log文件
        //ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //VpnConfigDao vpnConfigDao = (VpnConfigDao) ctx.getBean("dVpnConfigDao");
        //VpnServersDao vpnServersDao = (VpnServersDao) ctx.getBean("dVpnServersDao");
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        List<VpnConfig> jsonconfig = new ArrayList<>();
        List<VpnServers> jsonserver = new ArrayList<>();
        try {
            BufferedReader brname = new BufferedReader(new FileReader("D:/Python/list.log"));
            String sname = null;
            while ((sname = brname.readLine()) != null) {
                System.out.println("json data  "+sname);
                JSONObject dataJson = JSON.parseObject(sname);
                JSONObject df = dataJson.getJSONObject("DEFAULT");
                JSONArray array = df.getJSONArray("servers");
                if( array.size() > 0 ){
                    int count = 0;
                    for(int i=1 ; i<array.size() ; i++){
                        JSONObject item = array.getJSONObject(i);
                        if (item != null) {
                            VpnServers servers = new VpnServers();
                            count = count + 1;
                            String country = item.getString("country");
                            String ip = item.getString("domain_name");
                            //String server_load = item.getString("server_load");
                            servers.setCountry(country);
                            servers.setIp(ip);
                            servers.setServer_load(String.valueOf(count));
                            jsonserver.add(servers);
                        }
                    }
                }
                JSONObject json = df.getJSONObject("config");
                VpnConfig config = new VpnConfig();
                config.setPsk(json.getString("psk").toString());
                config.setEap_user(json.getString("eap_user").toString());
                config.setRemote_id(json.getString("remote_id").toString());
                config.setEap_passwd(json.getString("eap_passwd").toString());
                config.setLocal_id(json.getString("local_id").toString());
                config.setType(json.getString("type").toString());
                jsonconfig.add(config);
            }
            brname.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(time+" python脚本抓的配置列表大小  "+jsonconfig.size());
        System.out.println(time+" python脚本抓的IP列表大小  "+jsonserver.size());
        for (VpnServers s : jsonserver){
            System.out.println(s.getCountry() + "   " + s.getServer_load() + "   " +s.getIp());
        }
*/



        /*List<VpnConfig> configlist = vpnConfigDao.findAll();
        List<VpnServers> serverlist = new ArrayList<>();
        try{
            if (configlist.size() > 0 && jsonconfig.size() > 0 && jsonserver.size() > 0) {
                //从数据库查出和新数据一样国家和ip的数组的大小来
                for (VpnServers vp : jsonserver) {
                    List<VpnServers> server = vpnServersDao.findByIpAndContry(vp.getIp(), vp.getCountry());
                    for(VpnServers vb : server){
                        VpnServers sd = new VpnServers();
                        sd.setCountry(vb.getCountry());
                        sd.setIp(vb.getIp());
                        serverlist.add(sd);
                    }
                }
                System.out.println(time+" 数据库查出和新数据一样国家和ip的数组的大小  " + serverlist.size());
                //如果相同数据一致则不更新sever表，反之则下架旧IP，添加新IP
                if (serverlist.size() != jsonserver.size()) {
                    int rows = vpnServersDao.updateAll();
                    if (rows > 0) {
                        System.out.println(time + " 下架旧vpnserver ip " + rows + " 条");
                    }
                    try {
                        int nums = 0;
                        for (VpnServers vp : jsonserver) {
                            VpnServers vs = new VpnServers();
                            vs.setCountry(vp.getCountry());
                            vs.setIp(vp.getIp());
                            vs.setServer_load(vp.getServer_load());
                            vs.setStatus(0);
                            vs.setFlagurl("http://i.iosfreemusic.com/flag/" + vp.getCountry() + ".png");
                            nums = vpnServersDao.insert(vs);
                        }
                        if (nums > 0) {
                            System.out.println(time + " 添加新vpnserver ip");
                        }
                    } catch (Exception e) {
                    }
                } else {
                    System.out.println(time + " 新sever和旧sever一致，不做修改");
                }
                //vpn 配置表遍历查询 是否修改 新旧数据一致则不变
                for (VpnConfig config : configlist) {
                    for (VpnConfig jconfig : jsonconfig) {
                        if (config.getPsk().equals(jconfig.getPsk()) && config.getEap_user().equals(jconfig.getEap_user()) && config.getRemote_id().equals(jconfig.getRemote_id()) &&
                                config.getEap_passwd().equals(jconfig.getEap_passwd()) && config.getLocal_id().equals(jconfig.getLocal_id())) {
                            System.out.println(time + " vpnconfig配置表不做修改");
                            continue;
                        } else {
                            int num = vpnConfigDao.updateById(config.getId());
                            if (num > 0) {
                                System.out.println(time + " 下架旧的vpnconfig " + num + " 条");
                            }

                            VpnConfig vp = new VpnConfig();
                            vp.setPsk(jconfig.getPsk());
                            vp.setRemote_id(jconfig.getRemote_id());
                            vp.setLocal_id(jconfig.getLocal_id());
                            vp.setEap_passwd(jconfig.getEap_passwd());
                            vp.setEap_user(jconfig.getEap_user());
                            vp.setType(jconfig.getType());
                            vp.setStatus(0);
                            int cof = vpnConfigDao.insert(vp);
                            if (cof > 0) {
                                System.out.println(time + " 添加新的vpnconfig " + cof + " 条");
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/


    }

}
