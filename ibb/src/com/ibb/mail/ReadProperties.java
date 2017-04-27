package com.ibb.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Ryan on 2017/4/5.
 */
public class ReadProperties {
    public static Properties pro = new Properties();

    static {
        InputStream in= ReadProperties.class.getResourceAsStream("/conf/jmail.properties");
        try {
            pro.load(in);
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        return pro.getProperty(key);
    }
    public static void main(String[] args) {

//    	robot@oceanbys.com
//    	Robot6666
        System.out.println(getValue("smtp"));
        System.out.println(getValue("username"));
        System.out.println(getValue("password"));

    }
}
