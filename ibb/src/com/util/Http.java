package com.util;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;

public class Http
{

    public static String sendPost(String url, String content)
            throws ClientProtocolException, IOException
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(url.trim());

        httpost.setEntity(new StringEntity(content, Consts.UTF_8));
        HttpResponse hresponse;
        String result = null;
        hresponse = httpclient.execute(httpost);
        HttpEntity entity = hresponse.getEntity();
        result = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        httpclient.getConnectionManager().shutdown();
        return result;
    }

    public static String sendGet(String url) throws ClientProtocolException,
            IOException
    {
        return sendGet(url, null);
    }

    public static String sendGet(String url, Map<String, String> header)
            throws ClientProtocolException, IOException
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url.trim());
        httpclient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);
        if (header != null)
        {
            for (String key : header.keySet())
            {
                String value = header.get(key);
                httpget.setHeader(key, value);
            }
        }

        HttpResponse hresponse;
        String result = null;
        hresponse = httpclient.execute(httpget);
        HttpEntity entity = hresponse.getEntity();
        result = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        httpclient.getConnectionManager().shutdown();
        return result;
    }

    /**
     * 避免HttpClient的”SSLPeerUnverifiedException: peer not authenticated”异常
     * 不用导入SSL证书
     * @author xingbow
     *
     */
    public static class WebClientDevWrapper {
        @SuppressWarnings("deprecation")
        public static org.apache.http.client.HttpClient wrapClient(org.apache.http.client.HttpClient base) {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @SuppressWarnings("unused")
                    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
                    @SuppressWarnings("unused")
                    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

                    public boolean isClientTrusted(X509Certificate[] arg0) {
                        // TODO Auto-generated method stub
                        return false;
                    }
                    public boolean isServerTrusted(X509Certificate[] arg0) {
                        // TODO Auto-generated method stub
                        return false;
                    }
                };
                ctx.init(null,new TrustManager[]{tm},null);
                SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("https", 443, ssf));
                ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
                return new DefaultHttpClient(mgr, base.getParams());
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public static String sendTLSGet(String url, Map<String, String> header)
            throws ClientProtocolException, IOException
    {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient = WebClientDevWrapper.wrapClient(httpclient);
        HttpGet httpget = new HttpGet(url.trim());
        httpclient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                20000);
        if (header != null)
        {
            for (String key : header.keySet())
            {
                String value = header.get(key);
                httpget.setHeader(key, value);
            }
        }

        HttpResponse hresponse;
        String result = null;
        hresponse = httpclient.execute(httpget);
        HttpEntity entity = hresponse.getEntity();
        result = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        httpclient.getConnectionManager().shutdown();
        return result;
    }

    public static void main(String[] args){
        try{
            String json = Http.sendGet("http://api2.pigmentapp.co/v3/books/data.json");
            System.out.println(json.substring(0,32));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
