package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ryan on 2017/3/11.
 */
public class AdResources implements Serializable {

    private static final long serialVersionUID = 7116413255413901660L;

    private Long id;
    private String pkg;
    private String country;
    private String sourcekey;
    private String typekey;
    private String type;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getSourcekey() {
        return sourcekey;
    }

    public void setSourcekey(String sourcekey) {
        this.sourcekey = sourcekey;
    }

    public String getTypekey() {
        return typekey;
    }

    public void setTypekey(String typekey) {
        this.typekey = typekey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
