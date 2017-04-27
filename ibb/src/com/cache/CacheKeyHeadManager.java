package com.cache;

public enum CacheKeyHeadManager {
	
	adControlByPkg_Country_Head("AD_CONTROL_LIST_"),
	ad_config_ByPkg_Country_Head("AD_config_");
	private final String value;
	
	CacheKeyHeadManager(String str)
	{
		this.value = str;
	}
	public String getHead()
	{
		return value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.value;
	}
}
