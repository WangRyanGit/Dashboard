package com.cache;


import com.factory.CacheFactory;
import com.google.protobuf.nano.MessageNano;
import com.ibb.dao.BaseDao;
import com.ibb.util.Base64Decoder;
import com.ibb.util.Base64Encoder;
import com.log.Log;

public class CacheManager extends BaseDao {
	
	public  static void cacheAdControl(String key,MessageNano v)
	{
		byte[] b = MessageNano.toByteArray(v);
		Base64Encoder encoder = new Base64Encoder();
		String a = encoder.GetEncoded(b);
		
		CacheFactory.add(key, a, CacheFactory.ONE_MONTH);
	}
	
	public  static void cacheProto(CacheKeyHeadManager head,String middle,MessageNano v)
	{
		byte[] b = MessageNano.toByteArray(v);
		Base64Encoder encoder = new Base64Encoder();
		String a = encoder.GetEncoded(b);
		
		CacheFactory.add(head+middle, a, CacheFactory.ONE_MONTH);
	}
	
	public  static <T extends MessageNano> T getProto(String key,T v)
	{
		try {
			String a = (String)CacheFactory.get(key);
			if(a == null)return null;
			Base64Decoder decoder = new Base64Decoder();
			byte[] b = decoder.GetDecoded(a);
		
			return (T)MessageNano.mergeFrom(v, b);
		} catch (Exception e) {
			Log.log(e);	
			e.printStackTrace();
			return null;
		}
	}
	public  void reload()
	{
		 /* try {
			  HashMap<String, DataProto.AdShowStrategyResponse> map3 = new HashMap<String,DataProto.AdShowStrategyResponse> ();
			  
			  	AdControlDao2 d = (AdControlDao2)SpringHelper.getBean("AdControlDao2");
			  	
			  	List<AdControlPojo> cList =  d.queryAlladControl();
	  
			  	HashMap<String, List<AdControlPojo>> map1 = new HashMap<String,List<AdControlPojo>> ();
				for (AdControlPojo a : cList) {
					String key = CacheKeyHeadManager.adControlByPkg_Country_Head+"_"+a.getPkg_name()+"_"+a.getCountry();
					
						if(map1.get(key)!= null)
						{
							map1.get(key).add(a);
						}else
						{
							List<AdControlPojo> list = new ArrayList<AdControlPojo>();
							list.add(a);
							map1.put(key, list);
						}
						
				}
				
				
				List<AdStrategyPojo> sList =  d.queryAlladStrategy();
				
				
				HashMap<String, List<AdStrategyPojo>> map2 = new HashMap<String,List<AdStrategyPojo>> ();
				for (AdStrategyPojo a : sList) {
					String key = CacheKeyHeadManager.adControlByPkg_Country_Head+"_"+a.getPkg_name()+"_"+a.getCountry();
					
						if(map2.get(key)!= null)
						{
							map2.get(key).add(a);
						}else
						{
							List<AdStrategyPojo> list = new ArrayList<AdStrategyPojo>();
							list.add(a);
							map2.put(key, list);
						}
						
				}
				if(cList != null ){
					for (String key : map1.keySet()) {
						DataProto.AdShowStrategyResponse response = new DataProto.AdShowStrategyResponse();
						List<AdControlPojo> cListt = map1.get(key);
						DataProto.AdControl[] ads = new DataProto.AdControl[cListt.size()];
						for (int i = 0; i < cListt.size(); i++) {
							AdControlPojo pojo = cListt.get(i);
							DataProto.AdControl ad = new DataProto.AdControl();
							ad.positionID = pojo.getPosition();
							ad.show = pojo.getShow_on();
							ad.init = pojo.getInit_on();
							ad.requestIntervalTime =pojo.getRequest_interval();
							ads[i] = ad;
						}
						List<AdStrategyPojo> sListt = map2.get(key);
						if(sListt != null)
						{
							DataProto.AdStrategy[] sads = new DataProto.AdStrategy[sListt.size()];
							for (int i = 0; i < sListt.size(); i++) {
								AdStrategyPojo pojo = sListt.get(i);
								DataProto.AdStrategy ad = new DataProto.AdStrategy();
								ad.positionID = pojo.getPosition_id();
								ad.adSource = pojo.getAd_source();
								ad.adType = pojo.getAd_type();
								ad.priority = pojo.getPriority();
								ad.adID = pojo.getAd_id();
								sads[i] = ad;
							}
							response.adStrategyList =sads;
						}
						response.adControlList =ads;
						map3.put(key, response);
						
					}
				}
			
			
				if(sList != null ){	
					for (String key : map2.keySet()) {
						if(map3.get(key) != null)continue;
						DataProto.AdShowStrategyResponse response = new DataProto.AdShowStrategyResponse();
						List<AdStrategyPojo> sListt = map2.get(key);
						DataProto.AdStrategy[] sads = new DataProto.AdStrategy[sListt.size()];
						for (int i = 0; i < sListt.size(); i++) {
							AdStrategyPojo pojo = sListt.get(i);
							DataProto.AdStrategy ad = new DataProto.AdStrategy();
							ad.positionID = pojo.getPosition_id();
							ad.adSource = pojo.getAd_source();
							ad.adType = pojo.getAd_type();
							ad.priority = pojo.getPriority();
							ad.adID = pojo.getAd_id();
							sads[i] = ad;
						}
						response.adStrategyList =sads;
						List<AdControlPojo> cListt = map1.get(key);
						if(cListt != null)
						{
							DataProto.AdControl[] ads = new DataProto.AdControl[cListt.size()];
							for (int i = 0; i < cListt.size(); i++) {
								AdControlPojo pojo = cListt.get(i);
								DataProto.AdControl ad = new DataProto.AdControl();
								ad.positionID = pojo.getPosition();
								ad.show = pojo.getShow_on();
								ad.init = pojo.getInit_on();
								ad.requestIntervalTime =pojo.getRequest_interval();
								ads[i] = ad;
							}
							response.adControlList = ads;
						}
						map3.put(key,response);
						
					}
				
				}
				List<AdglobeControlPojo> gList = d.queryScreenLockList();
				if(gList != null && gList.size()>0)
				{
					for (AdglobeControlPojo a : gList) {
						String key = CacheKeyHeadManager.adControlByPkg_Country_Head+"_"+a.getPkg_name()+"_"+a.getCountry();
						DataProto.AdShowStrategyResponse response = map3.get(key);
						if(response != null)
						{
							*//*response.screenLock = a.isScreen_lock();*//*
						}else{	
							DataProto.AdShowStrategyResponse responset = new DataProto.AdShowStrategyResponse();
							*//*responset.screenLock = a.isScreen_lock();*//*
							map3.put(key, responset);
						}
						
						
						
					}
				}
				if(map3.keySet().size() > 0){
					CacheFactory.flushAll();
				}
				for (String key : map3.keySet()) {
					DataProto.AdShowStrategyResponse response = map3.get(key);
					CacheFactory.delete(key);
					CacheManager.cacheAdControl(key, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.log(e);
				
			}*/
		  
	}
	
	public void refreshAdConfig(){
		
		/*AdControlDao2 d = (AdControlDao2)SpringHelper.getBean("AdControlDao2");
		List<Map<String,String>> list = d.queryCacheDataAll();
		for (Map<String, String> map : list) {
			
			Set<String> set = map.keySet();
			for (String string : set) {
				String pkg = string;
				String country = map.get(string);
			
				String key = CacheKeyHeadManager.adControlByPkg_Country_Head.getHead()+"_"+pkg+"_"+country.toLowerCase();
				
				*//*DataProto.ConfigResponse c = d.queryCacheData(pkg, country);*//*
				CacheFactory.delete(key);
				*//*CacheManager.cacheAdControl(key, c);*//*
			}
		}*/
		
	}
}
