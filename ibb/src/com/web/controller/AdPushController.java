package com.web.controller;

import com.ibb.bean.AdPush;
import com.web.pojo.PageBean;
import com.web.service.AdPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/4/22.
 */
@Controller
@RequestMapping("/push")
public class AdPushController {

    @Autowired
    private AdPushService adPushService;

    @RequestMapping("/showAllAdPush")
    public String showAllAdPush(@RequestParam(value="currentPage",defaultValue="1",required = false)int currPage, @RequestParam(value="pkg",defaultValue="com",required=false)String pkg,Model model){
        //PageBean<AdPush> lists = adPushService.findByPage(currPage);
        PageBean<AdPush> lists = adPushService.findByPkgPage(currPage,pkg);
        model.addAttribute("pagelist",lists);
        return "adpush/list";
    }
    /**
     * 跳转到添加策略界面
     */
    @RequestMapping("/toAddAdPush")
    public String toAddAdPush(){
        return "adpush/add";
    }
    /**
     * 添加添加并重定向
     */
    @RequestMapping("/addAdPush")
    public String addAdPush(AdPush adPush){
        adPushService.insert(adPush);
        System.out.println("添加：" + adPush.getPkg()+ " 下 "+adPush.getMessage());
        return "redirect:/push/showAllAdPush";
    }
    /**
     * 跳转到编辑界面
     */
    @RequestMapping("/toEditAdPush")
    public String toEditAdPush(Long id,Model model){
        model.addAttribute("adpush",adPushService.findById(id));
        return "adpush/edit";
    }
    /**
     *编辑界面
     */
    @RequestMapping("/editAdPush")
    public String editAdPush(AdPush adPush,Model model){
        adPushService.update(adPush);
        adPush = adPushService.findById(adPush.getId());
        model.addAttribute("adPush", adPush);
        return "redirect:/push/showAllAdPush";
    }
    /**
     * 单个删除
     */
    @RequestMapping("/deleteAdPush")
    public String deleteAdPush(Long id) {
        adPushService.deleteById(id);
        return "redirect:/push/showAllAdPush";
    }
    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete")
    public String batchDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("check");
        if(ids == null){
            return "redirect:/push/showAllAdPush";
        }else{
            for (int i = 0; i < ids.length; i++) {
                Long id=Long.parseLong(ids[i]);
                adPushService.deleteById(id);
            }
            return "redirect:/push/showAllAdPush";
        }
    }

}
