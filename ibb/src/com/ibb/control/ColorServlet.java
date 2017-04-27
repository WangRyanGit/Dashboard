package com.ibb.control;

import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.util.SpringHelper;

/**
 * Created by Ryan on 2017/4/12.
 */
public class ColorServlet extends BaseServlet {
    private static final long serialVersionUID = -2007499960482046910L;

    @Override
    public ResponseBean process(RequestBean request) {
        BaseServlet servlet = (BaseServlet) SpringHelper
                .getBean("colorServlet");
        return servlet.process(request);
    }
}
