package com.ibb.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibb.bean.VpnConfig;
import com.ibb.bean.VpnServers;
import com.ibb.dao.VpnConfigDao;
import com.ibb.dao.VpnServersDao;
import com.util.SpringHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryan on 2017/3/25.
 */
public class VpnServerPython {
    public void work(){
        VpnConfigDao vpnConfigDao = (VpnConfigDao) SpringHelper.getBean("dVpnConfigDao");
        VpnServersDao vpnServersDao = (VpnServersDao) SpringHelper.getBean("dVpnServersDao");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        List<VpnConfig> jsonconfig = new ArrayList<>();
        List<VpnServers> jsonserver = new ArrayList<>();
        try {
            BufferedReader brname = new BufferedReader(new FileReader("/data/tools/cron_manager/server.log"));
            String sname = null;
            while ((sname = brname.readLine()) != null) {
                System.out.println("json data  "+sname);
                JSONObject dataJson = JSON.parseObject(sname);
                JSONObject df = dataJson.getJSONObject("DEFAULT");
                JSONArray array = df.getJSONArray("servers");
                if( array.size() > 0 ){
                    for(int i=1 ; i<array.size() ; i++){
                        JSONObject item = array.getJSONObject(i);
                        if (item != null) {
                            VpnServers servers = new VpnServers();
                            String country = item.getString("country");
                            String ip = item.getString("domain_name");
                            String server_load = item.getString("server_load");
                            servers.setCountry(country);
                            servers.setIp(ip);
                            servers.setServer_load(server_load);
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
        List<VpnConfig> configlist = vpnConfigDao.findAll();
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
                    int free = vpnServersDao.updateFree();
                    if (free > 0){
                        System.out.println("免费vpn "+free+" 条");
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
        }
    }
}
