package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ryan on 2017/3/24.
 */
public class VpnConfig implements Serializable {
    private static final long serialVersionUID = -8369557398602296615L;

    private Integer id;
    private String psk;
    private String eap_user;
    private String remote_id;
    private String eap_passwd;
    private String local_id;
    private String type;
    private Integer status;
    private Date createDate;
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getEap_user() {
        return eap_user;
    }

    public void setEap_user(String eap_user) {
        this.eap_user = eap_user;
    }

    public String getRemote_id() {
        return remote_id;
    }

    public void setRemote_id(String remote_id) {
        this.remote_id = remote_id;
    }

    public String getEap_passwd() {
        return eap_passwd;
    }

    public void setEap_passwd(String eap_passwd) {
        this.eap_passwd = eap_passwd;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
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
