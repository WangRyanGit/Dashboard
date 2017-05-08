package com.web.controller;

import com.ibb.bean.FollowPurchase;
import com.web.pojo.PageBean;
import com.web.service.FollowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Ryan on 2017/5/8.
 */
@Controller
@RequestMapping("/followcontrol")
public class FollowController {

    @Autowired
    private FollowDataService followDataService;

    @RequestMapping("/showAllFollowData")
    public String showAllFollowData(@RequestParam(value="currentPage",defaultValue="1",required = false)int currPage, @RequestParam(value="pkg",defaultValue="com",required=false)String pkg, @RequestParam(value="user_id",defaultValue="",required=false)String user_id, Model model){
        PageBean<FollowPurchase> lists = followDataService.findData(currPage,pkg,user_id);
        model.addAttribute("pagelist",lists);
        return "follow/list";
    }

    @RequestMapping("/toEditFollowData")
    public String toEditFollowData(Long id,Model model){
        FollowPurchase follow = followDataService.findById(id);
        model.addAttribute("follow",follow);
        return "follow/edit";
    }

    @RequestMapping("/editFollowPurchase")
    public String editFollowPurchase(FollowPurchase followPurchase){
        followDataService.update(followPurchase);
        return "redirect:/followcontrol/showAllFollowData";
    }
}
