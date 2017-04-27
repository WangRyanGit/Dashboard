package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2016/12/7.
 */
public class AdStrategy implements Serializable {

    private Long id;
    private String adId;
    private String pkg;
    private String country;
    private Integer adType;
    private Integer adSource;
    private Integer position;
    private Integer priority;
    private Integer adreward;
    private Integer minVersion;
    private Integer maxVersion;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public Integer getAdreward() {
        return adreward;
    }

    public void setAdreward(Integer adreward) {
        this.adreward = adreward;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
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

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public Integer getAdSource() {
        return adSource;
    }

    public void setAdSource(Integer adSource) {
        this.adSource = adSource;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(Integer minVersion) {
        this.minVersion = minVersion;
    }

    public Integer getMaxVersion() {
        return maxVersion;
    }

    public void setMaxVersion(Integer maxVersion) {
        this.maxVersion = maxVersion;
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
