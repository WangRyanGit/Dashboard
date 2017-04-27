package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ryan on 2017/3/21.
 */
public class VpnServers implements Serializable {
    private static final long serialVersionUID = -3055408711457337787L;

    private Integer id;
    private String country;
    private String server_load;
    private String ip;
    private String flagurl;
    private String free;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getServer_load() {
        return server_load;
    }

    public void setServer_load(String server_load) {
        this.server_load = server_load;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFlagurl() {
        return flagurl;
    }

    public void setFlagurl(String flagurl) {
        this.flagurl = flagurl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
