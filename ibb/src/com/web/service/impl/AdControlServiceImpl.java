package com.web.service.impl;

import com.ibb.bean.AdControl;
import com.ibb.dao.AdControlDao;
import com.web.pojo.PageBean;
import com.web.service.AdControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/4/21.
 */
@Service
public class AdControlServiceImpl implements AdControlService {

    @Autowired
    private AdControlDao adControlDao;

    @Override
    public void deleteById(Long id) {
        adControlDao.deleteById(id);
    }

    @Override
    public void insert(AdControl adControl) {
        adControlDao.insert(adControl);
    }

    @Override
    public void update(AdControl adControl) {
        adControlDao.update(adControl);
    }

    @Override
    public AdControl findById(Long id) {
        return adControlDao.findById(id);
    }

    @Override
    public List<AdControl> findAll() {
        return adControlDao.findAll();
    }

    @Override
    public int findCount() {
        return adControlDao.findCount();
    }

    @Override
    public PageBean<AdControl> findByPage(int currPage) {
        PageBean<AdControl> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage);

        int pageSize = 16;
        pageBean.setPageSize(pageSize);

        int pageCount = adControlDao.findCount();
        pageBean.setTotalCount(pageCount);

        double pc = pageCount;
        Double totalPage = Math.ceil(pc/pageSize);
        pageBean.setTotalPage(totalPage.intValue());

        Integer start = (currPage - 1)*pageSize;
        List<AdControl> lists = adControlDao.findByPage(start,pageSize);
        pageBean.setLists(lists);

        return pageBean;
    }

    @Override
    public int findPkgCount(String pkg) {
        return adControlDao.findPkgCount(pkg);
    }

    @Override
    public PageBean<AdControl> findByPkgPage(int currPage, String pkg) {
        PageBean<AdControl> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage);

        int pageSize = 15;
        pageBean.setPageSize(pageSize);

        int pageCount = adControlDao.findPkgCount(pkg);
        pageBean.setTotalCount(pageCount);

        pageBean.setPkg(pkg);   //查询条件参数

        double pc = pageCount;
        Double totalPage = Math.ceil(pc/pageSize);
        pageBean.setTotalPage(totalPage.intValue());

        Integer start = (currPage - 1)*pageSize;
        List<AdControl> lists = adControlDao.findByPkgPage(pkg,start,pageSize);
        pageBean.setLists(lists);

        return pageBean;
    }
}
