package com.ibb.control;

import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.util.SpringHelper;

/**
 * Created by Ryan on 2017/3/22.
 */
public class VpnServlet extends BaseServlet {

    private static final long serialVersionUID = 1267482073228404979L;

    @Override
    public ResponseBean process(RequestBean request) {
        BaseServlet servlet = (BaseServlet) SpringHelper
                .getBean("vpnServlet");
        return servlet.process(request);
    }
}
