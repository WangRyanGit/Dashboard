package com.web.service;

/**
 * Created by Ryan on 2017/4/18.
 */
public interface AdminUserService {
    // 通过用户名及密码核查用户登录
    boolean login(String username,String password);

}
