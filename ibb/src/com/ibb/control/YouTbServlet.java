package com.ibb.control;

import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.util.SpringHelper;

/**
 * Created by Ryan on 2017/4/1.
 */
public class YouTbServlet extends BaseServlet {


    private static final long serialVersionUID = 9218983070326818959L;

    @Override
    public ResponseBean process(RequestBean request) {
        BaseServlet servlet = (BaseServlet) SpringHelper
                .getBean("youtbServlet");
        return servlet.process(request);
    }
}
