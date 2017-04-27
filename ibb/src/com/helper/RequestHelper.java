package com.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import com.factory.SrcFactory;
import com.ibb.bean.RequestBean;

public class RequestHelper {

	  public static RequestBean getReqBean(final HttpServletRequest request)
	            throws Exception
	    {
		    int src = SrcFactory.getSrc(request);
		    if(src == SrcFactory.SRC_API_END)return null;
		    RequestBean bean = null;
	        try
	        {
	        	bean = new RequestBean(false);
	            if (bean != null)
	            {
	                bean.setReqType(src);
	                bean.setSrc(src);
	                bean.setIp(getIpAddr(request));
	            }
	        }
	        catch (Exception e)
	        {
	            throw e;
	        }
	        byte[] buf = ReadPostData(request);
	        bean.setData(buf);
	        return bean;
	        
	    }

	    public static String getIpAddr(HttpServletRequest request)
	    {
	        String ip = request.getHeader("x-forwarded-for");
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	        {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	        {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	        {
	            ip = request.getRemoteAddr();
	        }
	        return ip;
	    }
	  public static byte[] ReadPostData(final HttpServletRequest request)
	            throws IOException
	    {
	        if (request == null)
	        {
	            return null;
	        }
	        InputStream in = null;
	        try
	        {
	            InputStream bis = null;
	            in = request.getInputStream();
	            String encoding = request.getHeader("Content-Encoding");
	            in = request.getInputStream();
	            if (encoding != null)
	            {
	                if (encoding.toLowerCase().contains("gzip"))
	                {
	                    bis = new GZIPInputStream(in);
	                }
	            }
	            else
	            {
	                bis = new BufferedInputStream(in);
	            }
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            // byte[] data = new byte[request.getContentLength()];
	            byte[] data;
	            byte[] buf = new byte[2048];
	            int len = 0;
	            int index = 0;
	            while ((len = bis.read(buf)) != -1)
	            {
	                baos.write(buf, 0, len);
	                index += len;
	            }
	            data = baos.toByteArray();
	            baos.flush();
	            baos.close();
	            return data;
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	            throw e;
	        }
	        finally
	        {
	            if (in != null)
	                in.close(); // 关闭输入流
	        }
	    }
}
