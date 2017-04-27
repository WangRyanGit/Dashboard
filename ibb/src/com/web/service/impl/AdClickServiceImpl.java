package com.web.service.impl;

import com.ibb.bean.AdClick;
import com.ibb.bean.AdControl;
import com.ibb.dao.AdClickDao;
import com.web.pojo.PageBean;
import com.web.service.AdClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/4/21.
 */
@Service
public class AdClickServiceImpl implements AdClickService {


    @Autowired
    private AdClickDao adClickDao;

    @Override
    public void deleteById(Long id) {
        adClickDao.deleteById(id);
    }

    @Override
    public void insert(AdClick adClick) {
        adClickDao.insert(adClick);
    }

    @Override
    public void update(AdClick adClick) {
        adClickDao.update(adClick);
    }

    @Override
    public AdClick findById(Long id) {
        return adClickDao.findById(id);
    }

    @Override
    public List<AdClick> findAll() {
        return adClickDao.findAll();
    }

    @Override
    public int findCount() {
        return adClickDao.findCount();
    }

    @Override
    public int findPkgCount(String pkg) {
        return adClickDao.findPkgCount(pkg);
    }

    @Override
    public PageBean<AdClick> findByPage(int currPage) {
        PageBean<AdClick> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 16;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = adClickDao.findCount();
        pageBean.setTotalCount(pageCount);  //总数据条数

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<AdClick> list = adClickDao.findByPage(start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }

    //做pkg分类分页
    @Override
    public PageBean<AdClick> findByPkgPage(int currPage, String pkg) {
        PageBean<AdClick> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 15;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        pageBean.setPkg(pkg);   //查询条件参数

        int pageCount = adClickDao.findPkgCount(pkg);
        pageBean.setTotalCount(pageCount);  //总数据条数

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<AdClick> list = adClickDao.findByPkgPage(pkg,start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }


}
