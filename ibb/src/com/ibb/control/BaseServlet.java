package com.ibb.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helper.RequestHelper;
import com.helper.ResponseHelper;
import com.ibb.bean.RequestBean;
import com.ibb.bean.ResponseBean;
import com.log.Log;

public abstract class BaseServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 5828401831894498712L;


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request,

                         HttpServletResponse response) {
        RequestBean bean = null;
        ResponseBean responsebean = null;

        try {
            request.setCharacterEncoding("utf-8");
            bean = RequestHelper.getReqBean(request);
            responsebean = process(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e);
            responsebean.setResult(1);
        }
        try {
            ResponseHelper.responseByte(response, responsebean);// 输出处理结果
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(e);
        }

    }

    public abstract ResponseBean process(RequestBean request);
}
