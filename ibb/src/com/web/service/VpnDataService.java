package com.web.service;

import com.web.pojo.PageBean;
import com.web.pojo.VpnData;

/**
 * Created by Ryan on 2017/5/6.
 */
public interface VpnDataService {

    int findCount(String pkg,Long userid,String regtime); //总记录数
    PageBean<VpnData> findData(int currPage,String pkg,Long userid,String regtime);//分页

}
