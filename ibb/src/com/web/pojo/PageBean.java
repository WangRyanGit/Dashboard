package com.web.pojo;

import java.util.List;

/**
 * 分页实体类
 * @author Ryan
 * */
public class PageBean<T> {

	private int currPage;//当前页数
    private int pageSize;//每页显示的记录数
    private int totalCount;//总记录数
    private int totalPage;//总页数
    private List<T> lists;//每页的显示的数据

	private String pkg;
	private String adid;
	private Integer position;//其他五项用

	private Long userid;//vpn用
	private String regtime;

	private String user_id;//follow用
    
    public PageBean(){
    	super();
    }
    
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getAdid() {
		return adid;
	}

	public void setAdid(String adid) {
		this.adid = adid;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getRegtime(){
    	return regtime;
	}
	public void setRegtime(String regtime){
		this.regtime = regtime;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
