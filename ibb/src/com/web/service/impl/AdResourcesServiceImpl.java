package com.web.service.impl;

import com.ibb.bean.AdResources;
import com.ibb.dao.AdResourcesDao;
import com.web.pojo.PageBean;
import com.web.service.AdResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/4/22.
 */
@Service
public class AdResourcesServiceImpl implements AdResourcesService {

    @Autowired
    private AdResourcesDao adResourcesDao;
    @Override
    public void deleteById(Long id) {
        adResourcesDao.deleteById(id);
    }

    @Override
    public void insert(AdResources adResources) {
        adResourcesDao.insert(adResources);
    }

    @Override
    public void update(AdResources adResources) {
        adResourcesDao.update(adResources);
    }

    @Override
    public AdResources findById(Long id) {
        return adResourcesDao.findById(id);
    }

    @Override
    public List<AdResources> findAll() {
        return adResourcesDao.findAll();
    }

    @Override
    public int findCount() {
        return adResourcesDao.findCount();
    }

    @Override
    public PageBean<AdResources> findByPage(int currPage) {
        PageBean<AdResources> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 10;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = adResourcesDao.findCount();
        pageBean.setTotalCount(pageCount);  //总数据条数

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<AdResources> list = adResourcesDao.findByPage(start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }

    @Override
    public int findPkgCount(String pkg) {
        return adResourcesDao.findPkgCount(pkg);
    }

    @Override
    public PageBean<AdResources> findByPkgPage(int currPage, String pkg) {
        PageBean<AdResources> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 10;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = adResourcesDao.findPkgCount(pkg);
        pageBean.setTotalCount(pageCount);  //总数据条数

        pageBean.setPkg(pkg);   //查询条件参数

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<AdResources> list = adResourcesDao.findByPkgPage(pkg,start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }
}
