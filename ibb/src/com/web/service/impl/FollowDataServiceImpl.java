package com.web.service.impl;

import com.ibb.bean.FollowPurchase;
import com.ibb.bean.VpnPurchase;
import com.ibb.dao.FollowPurchaseDao;
import com.web.pojo.PageBean;
import com.web.pojo.VpnData;
import com.web.service.FollowDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/5/8.
 */
@Service
public class FollowDataServiceImpl implements FollowDataService {

    @Autowired
    private FollowPurchaseDao followPurchaseDao;

    @Override
    public void update(FollowPurchase followPurchase) {
        followPurchaseDao.update(followPurchase);
    }

    @Override
    public FollowPurchase findById(Long id) {
        return followPurchaseDao.findById(id);
    }

    @Override
    public int findCount(String pkg, String user_id) {
        return followPurchaseDao.findCount(pkg, user_id);
    }

    @Override
    public PageBean<FollowPurchase> findData(int currPage,String pkg, String user_id) {
        PageBean<FollowPurchase> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 20;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = followPurchaseDao.findCount(pkg,user_id);
        pageBean.setTotalCount(pageCount);  //总数据条数

        pageBean.setPkg(pkg);   //查询条件参数
        pageBean.setUser_id(user_id);

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<FollowPurchase> list = followPurchaseDao.findData(pkg,user_id,start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }
}
