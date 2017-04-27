package com.web.controller;

import com.ibb.bean.AdControl;
import com.web.pojo.PageBean;
import com.web.service.AdControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/4/21.
 */
@Controller
@RequestMapping("/control")
public class AdController {

    @Autowired
    private AdControlService adControlService;

    @RequestMapping("/showAllAdControl")
    public String showAllAdControl(@RequestParam(value="currentPage",defaultValue="1",required=false)int currPage,@RequestParam(value="pkg",defaultValue="com",required=false)String pkg, Model model){
        //PageBean<AdControl> lists = adControlService.findByPage(currPage);
        PageBean<AdControl> lists = adControlService.findByPkgPage(currPage,pkg);
        model.addAttribute("pagelist",lists);
        return "adcontrol/list";
    }
    /**
     * 跳转到添加界面
     */
    @RequestMapping("/toAddAdControl")
    public String toAddAdControl(){
        return "adcontrol/add";
    }
    /**
     * 添加添加并重定向
     */
    @RequestMapping("/addAdControl")
    public String addAdControl(AdControl adControl){
        adControlService.insert(adControl);
        System.out.println("添加：" + adControl.getPkg()+ " 下 "+adControl.getPosition());
        return "redirect:/control/showAllAdControl";
    }
    /**
     * 跳转到编辑界面
     */
    @RequestMapping("/toEditAdControl")
    public String toEditAdControl(Long id,Model model){
        model.addAttribute("adcontrol",adControlService.findById(id));
        return "adcontrol/edit";
    }
    /**
     *编辑策略界面
     */
    @RequestMapping("/editAdControl")
    public String editAdControl(AdControl adControl,Model model){
        adControlService.update(adControl);
        adControl = adControlService.findById(adControl.getId());
        model.addAttribute("adControl", adControl);
        return "redirect:/control/showAllAdControl";
    }
    /**
     * 单个删除
     */
    @RequestMapping("/deleteAdControl")
    public String deleteAdControl(Long id) {
        adControlService.deleteById(id);
        return "redirect:/control/showAllAdControl";
    }
    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete")
    public String batchDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("check");
        if(ids == null){
            return "redirect:/control/showAllAdControl";
        }else{
            for (int i = 0; i < ids.length; i++) {
                Long id=Long.parseLong(ids[i]);
                adControlService.deleteById(id);
            }
            return "redirect:/control/showAllAdControl";
        }
    }

}
