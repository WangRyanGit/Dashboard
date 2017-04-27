package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/12/7.
 * Updated by Ryan on 2017/3/3.
 */
public class AdControl implements Serializable {
    private Long id;
    private String pkg;
    private String country;
    private Integer position;
    private Integer showOn;
    private Integer initOn;
    private Integer requestInterval;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getShowOn() {
        return showOn;
    }

    public void setShowOn(Integer showOn) {
        this.showOn = showOn;
    }

    public Integer getInitOn() {
        return initOn;
    }

    public void setInitOn(Integer initOn) {
        this.initOn = initOn;
    }

    public Integer getRequestInterval() {
        return requestInterval;
    }

    public void setRequestInterval(Integer requestInterval) {
        this.requestInterval = requestInterval;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
