package com.web.service;

import com.ibb.bean.FollowPurchase;
import com.web.pojo.PageBean;


/**
 * Created by Ryan on 2017/5/8.
 */
public interface FollowDataService {
    void update(FollowPurchase followPurchase);
    FollowPurchase findById(Long id);
    int findCount(String pkg,String user_id);
    PageBean<FollowPurchase> findData(int currPage, String pkg, String user_id);


}
