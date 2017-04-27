package com.web.service;

import com.ibb.bean.AdResources;
import com.web.pojo.PageBean;

import java.util.List;

/**
 * Created by Ryan on 2017/4/22.
 */
public interface AdResourcesService {
    void deleteById(Long id);
    void insert(AdResources adResources);
    void update(AdResources adResources);
    AdResources findById(Long id);
    List<AdResources> findAll();   //查找所有

    int findCount();  //总记录数（方法暂时作废）
    PageBean<AdResources> findByPage(int currPage);//（方法暂时作废）

    int findPkgCount(String pkg); //总pkg记录数
    PageBean<AdResources> findByPkgPage(int currPage,String pkg);//根据pkg分页
}
