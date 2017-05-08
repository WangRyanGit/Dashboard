package com.web.service.impl;

import com.ibb.dao.VpnUsersDao;
import com.web.pojo.PageBean;
import com.web.pojo.VpnData;
import com.web.service.VpnDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ryan on 2017/5/6.
 */
@Service
public class VpnDataServiceImpl implements VpnDataService {

    @Autowired
    private VpnUsersDao vpnUsersDao;

    @Override
    public int findCount(String pkg, Long userid, String regtime) {
        return vpnUsersDao.findCount(pkg, userid, regtime);
    }

    @Override
    public PageBean<VpnData> findData(int currPage,String pkg, Long userid, String regtime) {
        PageBean<VpnData> pageBean = new PageBean<>();
        pageBean.setCurrPage(currPage); //设置当前页

        int pageSize = 20;
        pageBean.setPageSize(pageSize);  //设置每页显示数据条数

        int pageCount = vpnUsersDao.findCount(pkg,userid,regtime);
        pageBean.setTotalCount(pageCount);  //总数据条数

        pageBean.setPkg(pkg);   //查询条件参数
        pageBean.setUserid(userid);
        pageBean.setRegtime(regtime);

        double pc = pageCount;
        Double totalPage = Math.ceil(pc / pageSize);//向上取整
        pageBean.setTotalPage(totalPage.intValue());  //总页数

        Integer start = (currPage - 1)*pageSize;    //设置其实位置 数据库条数下标从0开始，所以减一
        List<VpnData> list = vpnUsersDao.findData(pkg,userid,regtime,start,pageSize);
        pageBean.setLists(list);

        return pageBean;
    }
}
