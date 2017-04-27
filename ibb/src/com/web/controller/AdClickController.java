package com.web.controller;

import com.ibb.bean.AdClick;
import com.web.pojo.PageBean;
import com.web.service.AdClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Ryan on 2017/4/21.
 */
@Controller
@RequestMapping("/click")
public class AdClickController {

    @Autowired
    private AdClickService adClickService;

    @RequestMapping("/showAllAdClick")
    public String showAllAdClick(@RequestParam(value="currentPage",defaultValue="1",required=false)int currPage,@RequestParam(value="pkg",defaultValue="com",required=false)String pkg, Model model){
        //List<AdClick> pkgs = adClickService.findPkg();
        //model.addAttribute("pkglist",pkgs);
        PageBean<AdClick> lists = adClickService.findByPkgPage(currPage,pkg);
        System.out.println(lists.getLists().size());
        model.addAttribute("pagelist",lists);
        return "adclick/list";
    }
    /**
     * 跳转到添加界面
     */
    @RequestMapping("/toAddAdClick")
    public String toAddAdClick(){
        return "adclick/add";
    }
    /**
     * 添加添加并重定向
     */
    @RequestMapping("/addAdClick")
    public String addAdClick(AdClick adClick){
        adClickService.insert(adClick);
        return "redirect:/click/showAllAdClick";
    }
    /**
     * 跳转到编辑界面
     */
    @RequestMapping("/toEditAdClick")
    public String toEditAdClick(Long id,Model model){
        model.addAttribute("adclick",adClickService.findById(id));
        return "adclick/edit";
    }
    /**
     *编辑界面
     */
    @RequestMapping("/editAdClick")
    public String editAdClick(AdClick adclick,Model model){
        adClickService.update(adclick);
        adclick = adClickService.findById(adclick.getId());
        model.addAttribute("adclick", adclick);
        return "redirect:/click/showAllAdClick";
    }
    /**
     * 单个删除
     */
    @RequestMapping("/deleteAdClick")
    public String deleteAdClick(Long id) {
        adClickService.deleteById(id);
        return "redirect:/click/showAllAdClick";
    }
    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete")
    public String batchDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("check");
        if(ids == null){
            return "redirect:/click/showAllAdClick";
        }else{
            for (int i = 0; i < ids.length; i++) {
                Long id=Long.parseLong(ids[i]);
                adClickService.deleteById(id);
            }
            return "redirect:/click/showAllAdClick";
        }
    }
}
