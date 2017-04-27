package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/12/7.
 */
public class Apps implements Serializable {
    private Long id;
    private String pkg;
    private String version;
    private String url;
    private String icon;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
