package com.web.service.impl;

import com.ibb.dao.AppsDao;
import com.util.SpringHelper;
import com.web.dao.AdminUserDao;
import com.web.pojo.AdminUser;
import com.web.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Ryan on 2017/4/18.
 */

@Service//注解用于标示此类为业务层组件,在使用时会被注解的类会自动由spring进行注入,无需我们创建实例
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;
    //登录方法的实现 从jsp页面获取username与password
    @Override
    public boolean login(String username, String password) {
        System.out.println("Enter account: " + username + "  Enter password: " + password);
        List<AdminUser> admin = adminUserDao.findByName(username);
        Iterator iter = admin.iterator();
        while (iter.hasNext()){
            AdminUser ad = (AdminUser) iter.next();
            if (ad.getUsername().equals(username) && ad.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
