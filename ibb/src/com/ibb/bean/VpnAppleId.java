package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ryan on 2017/4/6.
 */
public class VpnAppleId implements Serializable {
    private static final long serialVersionUID = -8344780791052454986L;
    private Long id;
    private String pkg;
    private String appleid;
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

    public String getAppleid() {
        return appleid;
    }

    public void setAppleid(String appleid) {
        this.appleid = appleid;
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
