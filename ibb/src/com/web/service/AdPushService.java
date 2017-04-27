package com.web.service;

import com.ibb.bean.AdPush;
import com.web.pojo.PageBean;

import java.util.List;

/**
 * Created by Ryan on 2017/4/22.
 */
public interface AdPushService {
    void deleteById(Long id);
    void insert(AdPush adPush);
    void update(AdPush adPush);
    AdPush findById(Long id);
    List<AdPush> findAll();   //查找所有

    int findCount();  //总记录数（方法暂时作废）
    PageBean<AdPush> findByPage(int currPage);//（方法暂时作废）

    int findPkgCount(String pkg); //总pkg记录数
    PageBean<AdPush> findByPkgPage(int currPage,String pkg);//根据pkg分页
}
