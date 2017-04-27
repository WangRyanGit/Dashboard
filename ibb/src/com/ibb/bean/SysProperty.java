package com.ibb.bean;

public class SysProperty
{
    private static String webHttp;
    private String        uploadPath;
    private String        aaptPath;
    private String        resPath;
    private String        resList;
    private String        statis;
    private String        serverList;

    public static String getWebHttp()
    {
        return webHttp;
    }

    public static void setWebHttp(String webHttp)
    {
        SysProperty.webHttp = webHttp;
    }

    public String getUploadPath()
    {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath)
    {
        this.uploadPath = uploadPath;
    }

    public String getAaptPath()
    {
        return aaptPath;
    }

    public void setAaptPath(String aaptPath)
    {
        this.aaptPath = aaptPath;
    }

    public String getResPath()
    {
        return resPath;
    }

    public void setResPath(String resPath)
    {
        this.resPath = resPath;
    }

    public String getResList()
    {
        return resList;
    }

    public void setResList(String resList)
    {
        this.resList = resList;
    }

    public String getStatis()
    {
        return statis;
    }

    public void setStatis(String statis)
    {
        this.statis = statis;
    }

    public String getServerList()
    {
        return serverList;
    }

    public void setServerList(String serverList)
    {
        this.serverList = serverList;
    }

}
