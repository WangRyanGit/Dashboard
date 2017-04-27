package com.web.service;

import com.ibb.bean.AdStrategy;
import com.web.pojo.PageBean;

import java.util.List;

/**
 * Created by Ryan on 2017/4/20.
 */
public interface AdStrategyService {
    void deleteById(Long id);
    void insert(AdStrategy adStrategy);
    void update(AdStrategy adStrategy);
    AdStrategy findById(Long id);
    List<AdStrategy> findAll();   //查找所有

    int findCount();  //总记录数（方法暂时作废）
    PageBean<AdStrategy> findByPage(int currPage);//（方法暂时作废）

    int findPkgCount(String pkg,String adid,Integer position); //总pkg记录数
    PageBean<AdStrategy> findByPkgPage(int currPage,String pkg,String adid,Integer position);//根据pkg分页
}
