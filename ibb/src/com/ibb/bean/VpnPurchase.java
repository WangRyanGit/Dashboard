package com.ibb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ryan on 2017/4/10.
 */
public class VpnPurchase implements Serializable {
    private static final long serialVersionUID = -3262265118529751523L;

    private Long id;
    private String pkg;
    private Long user_id;
    private String quantity;
    private String product_id;
    private String purchase_date;
    private String expires_date;
    private String md5_receipt;     //拿到收据的MD5
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getExpires_date() {
        return expires_date;
    }

    public void setExpires_date(String expires_date) {
        this.expires_date = expires_date;
    }

    public String getMd5_receipt() {
        return md5_receipt;
    }

    public void setMd5_receipt(String md5_receipt) {
        this.md5_receipt = md5_receipt;
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
