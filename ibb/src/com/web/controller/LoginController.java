package com.web.controller;

import com.web.pojo.AdminUser;
import com.web.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ryan on 2017/4/18.
 */
@Controller
@RequestMapping("/dashboard")
public class LoginController {

    @Autowired
    private AdminUserService adminUserService;

    /*
    * 附加  由index跳到login界面
    * */
    @RequestMapping("/tologin")
    private String tologin(){
        return "/login";
    }

    /*
    * 附加  跳到main界面
    * */
    @RequestMapping("/tomain")
    private String tomain(){
        return "/main";
    }

    //login业务的访问位置为/dashboard/login
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String login(AdminUser user, HttpServletRequest request) throws Exception{

        //调用login方法来验证是否是注册用户
        boolean loginType = adminUserService.login(user.getUsername(),user.getPassword());
        if(loginType){
            //如果验证通过,则将用户信息传到前台
            request.setAttribute("username",user.getUsername());
            //并跳转到main.jsp页面
            return "/main";
        }else{
            //若不对,则将错误信息显示到错误页面error.jsp
            request.setAttribute("message","用户名密码错误");
            System.out.println("Access Denied Invalid Username/Password");
            return "/error";
        }

    }



}
