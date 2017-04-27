package com.helper;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.ibb.bean.ResponseBean;

public class ResponseHelper {
	  /**
     *将操作结果返回给
     * 
     * @throws Exception
     * */
    public static void responseByte(HttpServletResponse response,
            final ResponseBean bean) throws Exception
    {
        OutputStream out = null;
        try
        {
            response.setCharacterEncoding("utf-8");     //设置Response的编码方式为UTF-8
            response.setContentType("application/json");    //向浏览器发送一个响应
//            response.setContentType("text/html;charset=UTF-8");
            out = response.getOutputStream();
           
            byte[] buf = bean.getData();
            out.write(buf);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
    }
}
