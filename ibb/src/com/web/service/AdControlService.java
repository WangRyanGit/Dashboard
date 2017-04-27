package com.web.service;

import com.ibb.bean.AdControl;
import com.web.pojo.PageBean;

import java.util.List;

/**
 * Created by Ryan on 2017/4/21.
 */
public interface AdControlService {
    void deleteById(Long id);
    void insert(AdControl adControl);
    void update(AdControl adControl);
    AdControl findById(Long id);
    List<AdControl> findAll();   //查找所有

    int findCount();  //总记录数（方法暂时作废）
    PageBean<AdControl> findByPage(int currPage);//（方法暂时作废）

    int findPkgCount(String pkg); //总pkg记录数
    PageBean<AdControl> findByPkgPage(int currPage,String pkg);//根据pkg分页
}
