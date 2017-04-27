package com.web.service.impl;

import com.ibb.bean.AdStrategy;
import com.ibb.dao.AdStrategyDao;
import com.web.pojo.PageBean;
import com.web.service.AdStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/4/20.
 */
@Service
public class AdStrategyServiceImpl implements AdStrategyService {

    @Autowired
    private AdStrategyDao adStrategyDao;

    @Override
    public void deleteById(Long id) {
         adStrategyDao.deleteById(id);
    }

    @Override
    public void insert(AdStrategy adStrategy) {
        adStrategyDao.insert(adStrategy);
    }

    @Override
    public void update(AdStrategy adStrategy) {
        adStrategyDao.update(adStrategy);
    }

    @Override
    public AdStrategy findById(Long id) {
        return adStrategyDao.findById(id);
    }

    @Override
    public List<AdStrategy> findAll() {
        return adStrategyDao.findAll();
    }

    @Override
    public int findCount() {
        return adStrategyDao.findCount();
    }

    @Override
    public PageBean<AdStrategy> findByPage(int currPage) {
        PageBean<AdStrategy> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 30;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = adStrategyDao.findCount();
        pageBean.setTotalCount(pageCount);  //总数据条数

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<AdStrategy> list = adStrategyDao.findByPage(start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }

    @Override
    public int findPkgCount(String pkg,String adid,Integer position) {
        return adStrategyDao.findPkgCount(pkg,adid,position);
    }

    @Override
    public PageBean<AdStrategy> findByPkgPage(int currPage, String pkg,String adid,Integer position) {
        PageBean<AdStrategy> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 20;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = adStrategyDao.findPkgCount(pkg,adid,position);
        pageBean.setTotalCount(pageCount);  //总数据条数

        pageBean.setPkg(pkg);   //查询条件参数
        pageBean.setAdid(adid);
        pageBean.setPosition(position);

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<AdStrategy> list = adStrategyDao.findByPkgPage(pkg,adid,position,start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }
}
