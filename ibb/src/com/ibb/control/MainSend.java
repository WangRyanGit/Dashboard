package com.ibb.control;

import com.ibb.bean.AdPush;
import com.ibb.dao.AdPushDao;
import com.util.SpringHelper;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2017/3/20.
 */
public class MainSend {

    private static Log log = LogFactory.getLog(MainSend.class.getName());
    //ApplicationContext SpringHelper = new ClassPathXmlApplicationContext("applicationContext.xml");

    /************************************************
     测试推送服务器地址：gateway.sandbox.push.apple.com /2195
     产品推送服务器地址：gateway.push.apple.com / 2195
     需要javaPNS_2.2.jar包
     ***************************************************/
    /**
     *这是一个比较简单的推送方法，
     * apple的推送方法
     * @param tokens   iphone手机获取的token
     * @param path 这里是一个.p12格式的文件路径，需要去apple官网申请一个
     * @param password  p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
     * @param message 推送消息的内容
     * @param count 应用图标上小红圈上的数值
     * @param sendCount 单发还是群发  true：单发 false：群发
     */

    public void sendpush(List<String> tokens,String path, String password, String message,Integer count,boolean sendCount,String pkg) {

        try {
            //message是一个json的字符串{“aps”:{“alert”:”iphone推送测试”}}

            PushNotificationPayload payLoad =  PushNotificationPayload.fromJSON(message);

            AdPushDao AdPushDao = (AdPushDao) SpringHelper.getBean("dAdPushDao");
            List<AdPush> pushlist = AdPushDao.findByPkg(pkg);
            for (AdPush ad : pushlist){
                System.out.println(ad.getPkg()+" || "+ad.getMessage());
                String mess = ad.getMessage();
                payLoad.addAlert(mess); // 消息内容
            }
            //payLoad.addAlert("Check your INS followers!"); // 消息内容

            payLoad.addBadge(count); // iphone应用图标上小红圈上的数值

            payLoad.addSound("default"); // 铃音 默认



            PushNotificationManager pushManager = new PushNotificationManager();

            //true：表示的是产品发布推送服务 false：表示的是产品测试推送服务

            pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, password, true));

            List<PushedNotification> notifications = new ArrayList<PushedNotification>();
            // 发送push消息
            if (sendCount) {
                log.debug("--------------------------apple push Single-------");
                Device device = new BasicDevice();
                device.setToken(tokens.get(0));
                PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
                notifications.add(notification);
            } else {
                log.debug("--------------------------apple push Mass-------");
                List<Device> device = new ArrayList<Device>();
                for (String token : tokens) {
                    device.add(new BasicDevice(token));
                }
                notifications = pushManager.sendNotifications(payLoad, device);
            }
            List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
            List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
            int failed = failedNotifications.size();
            int successful = successfulNotifications.size();
            if (successful > 0 && failed == 0) {
                log.debug("-----All notifications pushed 成功 (" + successfulNotifications.size() + "):");
            } else if (successful == 0 && failed > 0) {
                log.debug("-----All notifications 失败 (" + failedNotifications.size() + "):");
            } else if (successful == 0 && failed == 0) {
                System.out.println("No notifications could be sent, probably because of a critical error");
            } else {
                log.debug("------Some notifications 失败 (" + failedNotifications.size() + "):");
                log.debug("------Others 成功 (" + successfulNotifications.size() + "):");
            }
             //pushManager.stopConnection();
            System.out.println(pkg + " end push");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO
     * @param args
     */
    public static void main(String[] args) {
        MainSend send=new MainSend();
        List<String> tokens=new ArrayList<String>();
        tokens.add("7b1c053b3d7d0ffbf65a08ca240ef81161fe6eac22e0183ddc74f5c7b150aac6");
        String path="E:\\iospush\\zypushDev.p12";
        ///data/tools/sendpush/zypushDev.p12
        String password="123456";
        String message="{'aps':{'alert':''}}";
        Integer count=1;
        String pkg = null;
        boolean sendCount=false;
        send.sendpush(tokens, path, password, message, count, sendCount,pkg);

    }

}
