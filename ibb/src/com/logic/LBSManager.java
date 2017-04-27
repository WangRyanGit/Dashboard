package com.logic;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;
import com.util.SpringHelper;

public class LBSManager
{
    /*private static DatabaseReader dbReader = null;
    
    public static Country getCountry(String mcc, String ip)
    {
    	
        CountryDao dao = (CountryDao) SpringHelper
                .getBean("dcountryDao");
        if (mcc !=null) {
//            Country country = dao.findByMcc(mcc);
//            if (country == null)
//            {
//                String c = getCountryByIp(ip);
//                country = dao.findByCountry(c);
//            }
            
            String c = getCountryByIp(ip);
            Country  country = dao.findByCountry(c);
            if (country == null) {
            	country = dao.findByMcc(mcc);
            }
            
            return country;
        } else {
            String c = getCountryByIp(ip);
            Country  country = dao.findByCountry(c);
        	return  country;
        }
    }
    static{
    	 if (dbReader == null)
         {
//             String path = LBSManager.class.getClassLoader().getResource("/")
//                     .getPath()
//                     + File.separator + "Country.mmdb";
             String path = "/home/centos/tools/myapp/battery/WEB-INF/classes"+ File.separator + "Country.mmdb";

             File database = new File(path);
             try
             {
                 dbReader = new DatabaseReader.Builder(database).build();
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }
         }
    }
    public static String getCountryByIp(String ip)
    {
        if (dbReader == null)
        {///home/centos/tools/myapp/battery/WEB-INF/classes
//            String path = LBSManager.class.getClassLoader().getResource("/home/centos/tools/myapp/battery/WEB-INF/classes")
//                    .getPath()
//                    + File.separator + "Country.mmdb";
            String path = "/home/centos/tools/myapp/battery/WEB-INF/classes"+ File.separator + "Country.mmdb";
            // GeoLite2Country.mmdb GeoLite2City.mmdb
            File database = new File(path);
            try
            {
                dbReader = new DatabaseReader.Builder(database).build();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        try
        {
            //String key = LBSManager.class.getName() + "_byip_" + ip;
            Object obj = null; //CacheFactory.get(key);
            if (obj != null && obj instanceof String)
            {
                return (String) obj;
            }
            else
            {
                String ret;
                InetAddress ipAddress = InetAddress.getByName(ip);
                // CityResponse response = dbReader.city(ipAddress);
                CountryResponse response = dbReader.country(ipAddress);
                com.maxmind.geoip2.record.Country country = response.getCountry();
                ret = country.getIsoCode();
//                CacheFactory
//                        .add(key, ret, CacheFactory.ONE_MONTH);
                return ret;
            }
        }
        catch (Exception e)
        {
           // e.printStackTrace();
        }
        return null;
    }*/
}
