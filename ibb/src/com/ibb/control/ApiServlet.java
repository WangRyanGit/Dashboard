package com.ibb.control;

import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.util.SpringHelper;

public class ApiServlet extends BaseServlet {
    /**
     *
     */
    private static final long serialVersionUID = 9136306206719498148L;

    @Override
    public ResponseBean process(RequestBean request) {
        BaseServlet servlet = (BaseServlet) SpringHelper
                .getBean("apiServlet");
        return servlet.process(request);
    }

}
