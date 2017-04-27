package com.web.controller;

import com.ibb.bean.AdStrategy;
import com.web.pojo.PageBean;
import com.web.service.AdStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/4/20.
 */
@Controller
@RequestMapping("/strategy")
public class AdStrategyController {

    @Autowired
    private AdStrategyService adStrategyService;

    @RequestMapping("/showAllAdStrategy")
    public String showAllAdStrategy(@RequestParam(value="currentPage",defaultValue="1",required = false)int currPage,@RequestParam(value="pkg",defaultValue="com",required=false)String pkg,@RequestParam(value="adid",defaultValue="",required=false)String adid,@RequestParam(value="position",defaultValue="0",required=false)Integer position, Model model){
        //PageBean<AdStrategy> lists = adStrategyService.findByPage(currPage);
        PageBean<AdStrategy> lists = adStrategyService.findByPkgPage(currPage,pkg,adid,position);
        model.addAttribute("pagelist",lists);
        return "adstrategy/list";
    }
    /**
     * 跳转到添加策略界面
     */
    @RequestMapping("/toAddAdStrategy")
    public String toAddAdStrategy(){
        return "adstrategy/add";
    }
    /**
     * 添加添加并重定向
     */
    @RequestMapping("/addAdStrategy")
    public String addAdStrategy(AdStrategy adStrategy){
        adStrategyService.insert(adStrategy);
        System.out.println("添加：" + adStrategy.getPkg()+ " 下 "+adStrategy.getAdId());
        return "redirect:/strategy/showAllAdStrategy";
    }
    /**
     * 跳转到编辑策略界面
     */
    @RequestMapping("/toEditAdStrategy")
    public String toEditAdStrategy(Long id,Model model){
        AdStrategy adStrategy = adStrategyService.findById(id);
        model.addAttribute("adStrategy",adStrategy);
        return "adstrategy/edit";
    }
    /**
     *编辑策略界面
     */
    @RequestMapping("/editAdStrategy")
    public String editAdStrategy(AdStrategy adStrategy){
        adStrategyService.update(adStrategy);
        return "redirect:/strategy/showAllAdStrategy";
    }
    /**
     * 单个删除
     */
    @RequestMapping("/deleteAdStrategy")
    public String deleteAdStrategy(Long id) {
        adStrategyService.deleteById(id);
        return "redirect:/strategy/showAllAdStrategy";
    }
    /**
     * 批量删除
     */
    @RequestMapping("/batchDelete")
    public String batchDelete(HttpServletRequest request) {
        String[] ids = request.getParameterValues("check");
        if(ids == null){
            return "redirect:/strategy/showAllAdStrategy";
        }else{
            for (int i = 0; i < ids.length; i++) {
                Long id=Long.parseLong(ids[i]);
                adStrategyService.deleteById(id);
            }
            return "redirect:/strategy/showAllAdStrategy";
        }
    }









}
