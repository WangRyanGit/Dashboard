package com.ibb.control;

import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.util.SpringHelper;

/**
 * Created by Ryan on 2017/4/17.
 */
public class FollowServlet extends BaseServlet {
    private static final long serialVersionUID = 7313558969818058560L;

    @Override
    public ResponseBean process(RequestBean request) {
        BaseServlet servlet = (BaseServlet) SpringHelper
                .getBean("followServlet");
        return servlet.process(request);
    }
}
