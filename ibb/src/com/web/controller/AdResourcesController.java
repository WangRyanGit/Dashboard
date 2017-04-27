package com.web.controller;

import com.ibb.bean.AdResources;
import com.web.pojo.PageBean;
import com.web.service.AdResourcesService;
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
@RequestMapping("/resources")
public class AdResourcesController {

    @Autowired
    private AdResourcesService adResourcesService;

    @RequestMapping("/showAllAdResources")
    public String showAllAdResources(@RequestParam(value="currentPage",defaultValue="1",required = false)int currPage,@RequestParam(value="pkg",defaultValue="com",required=false)String pkg, Model model){
        //PageBean<AdResources> lists = adResourcesService.findByPage(currPage);
        PageBean<AdResources> lists = adResourcesService.findByPkgPage(currPage,pkg);
        model.addAttribute("pagelist",lists);
        return "adresources/list";
    }
    /**
     * 跳转到添加策略界面
     */
    @RequestMapping("/toAddAdResources")
    public String toAddAdResources(){
        return "adresources/add";
    }
    /**
     * 添加添加并重定向
     */
    @RequestMapping(value = "/addAdResources",method= RequestMethod.POST)
    public String addAdResources(AdResources adResources){
        adResourcesService.insert(adResources);
        return "redirect:/resources/showAllAdResources";
    }
    /**
     * 跳转到编辑界面
     */
    @RequestMapping("/toEditAdResources")
    public String toEditAdResources(Long id,Model model){
        model.addAttribute("adresources",adResourcesService.findById(id));
        return "adresources/edit";
    }
    /**
     *编辑界面
     */
    @RequestMapping("/editAdResources")
    public String editAdResources(AdResources adResources,Model model){
        adResourcesService.update(adResources);
        adResources = adResourcesService.findById(adResources.getId());
        model.addAttribute("adResources", adResources);
        return "redirect:/resources/showAllAdResources";
    }
    /**
     * 单个删除
     */
    @RequestMapping("/deleteAdResources")
    public String deleteAdResources(Long id) {
        adResourcesService.deleteById(id);
        return "redirect:/resources/showAllAdResources";
    }
    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete")
    public String batchDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("check");
        if(ids == null){
            return "redirect:/resources/showAllAdResources";
        }else{
            for (int i = 0; i < ids.length; i++) {
                Long id=Long.parseLong(ids[i]);
                adResourcesService.deleteById(id);
            }
            return "redirect:/resources/showAllAdResources";
        }
    }

}
