package com.web.service;

import com.ibb.bean.AdClick;
import com.web.pojo.PageBean;

import java.util.List;

/**
 * Created by Ryan on 2017/4/21.
 */
public interface AdClickService {
    void deleteById(Long id);
    void insert(AdClick adClick);
    void update(AdClick adClick);
    AdClick findById(Long id);
    List<AdClick> findAll();   //查找所有

    int findCount();  //总记录数
    int findPkgCount(String pkg); //总pkg记录数
    PageBean<AdClick> findByPage(int currPage);
    PageBean<AdClick> findByPkgPage(int currPage,String pkg);
}
