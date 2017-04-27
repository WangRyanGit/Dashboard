package com.web.service.impl;

import com.ibb.bean.AdPush;
import com.ibb.dao.AdPushDao;
import com.web.pojo.PageBean;
import com.web.service.AdPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/4/22.
 */
@Service
public class AdPushServiceImpl implements AdPushService {

    @Autowired
    private AdPushDao adPushDao;
    @Override
    public void deleteById(Long id) {
        adPushDao.deleteById(id);
    }

    @Override
    public void insert(AdPush adPush) {
        adPushDao.insert(adPush);
    }

    @Override
    public void update(AdPush adPush) {
        adPushDao.update(adPush);
    }

    @Override
    public AdPush findById(Long id) {
        return adPushDao.findById(id);
    }

    @Override
    public List<AdPush> findAll() {
        return adPushDao.findAll();
    }

    @Override
    public int findCount() {
        return adPushDao.findCount();
    }

    @Override
    public PageBean<AdPush> findByPage(int currPage) {
        PageBean<AdPush> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage);

        int pageSize = 10;
        pageBean.setPageSize(pageSize);

        int totolCount = adPushDao.findCount();
        pageBean.setTotalCount(totolCount);

        double tc = totolCount;
        Double totalPage = Math.ceil(tc / pageSize);
        pageBean.setTotalPage(totalPage.intValue());

        int start = (currPage - 1) * pageSize;
        List<AdPush> lists = adPushDao.findByPage(start,pageSize);
        pageBean.setLists(lists);

        return pageBean;
    }

    @Override
    public int findPkgCount(String pkg) {
        return adPushDao.findPkgCount(pkg);
    }

    @Override
    public PageBean<AdPush> findByPkgPage(int currPage, String pkg) {
        PageBean<AdPush> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage);

        int pageSize = 10;
        pageBean.setPageSize(pageSize);

        int totolCount = adPushDao.findPkgCount(pkg);
        pageBean.setTotalCount(totolCount);

        pageBean.setPkg(pkg);   //查询条件参数

        double tc = totolCount;
        Double totalPage = Math.ceil(tc / pageSize);
        pageBean.setTotalPage(totalPage.intValue());

        int start = (currPage - 1) * pageSize;
        List<AdPush> lists = adPushDao.findByPkgPage(pkg,start,pageSize);
        pageBean.setLists(lists);

        return pageBean;
    }
}
