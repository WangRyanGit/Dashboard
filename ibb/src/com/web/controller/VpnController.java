package com.web.controller;

import com.web.pojo.PageBean;
import com.web.pojo.VpnData;
import com.web.service.VpnDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/5/6.
 */
@Controller
@RequestMapping("/vpncontrol")
public class VpnController {

    @Autowired
    private VpnDataService vpnDataService;

    @RequestMapping("/showAllVpnUserData")
    public String showAllVpnUserData(@RequestParam(value="currentPage",defaultValue="1",required = false)int currPage, @RequestParam(value="pkg",defaultValue="com",required=false)String pkg, @RequestParam(value="userid",defaultValue="0",required=false)Long userid, @RequestParam(value="regtime",defaultValue="",required=false)String regtime, Model model){
        PageBean<VpnData> lists = vpnDataService.findData(currPage,pkg,userid,regtime);
        model.addAttribute("pagelist",lists);
        return "vpn/list";
    }
}
