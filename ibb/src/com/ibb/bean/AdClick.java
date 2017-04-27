package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ryan on 2017/3/10.
 */
public class AdClick implements Serializable {

    private static final long serialVersionUID = 3544023659324868237L;
    private Long id;
    private String pkg;
    private String country;
    private Integer position;
    private String minClick;
    private String maxClick;
    private Integer status;
    private Date createDate;
    private Date updateDate;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMinClick() {
        return minClick;
    }

    public void setMinClick(String minClick) {
        this.minClick = minClick;
    }

    public String getMaxClick() {
        return maxClick;
    }

    public void setMaxClick(String maxClick) {
        this.maxClick = maxClick;
    }
}
