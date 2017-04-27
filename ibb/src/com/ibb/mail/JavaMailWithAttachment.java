package com.ibb.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Ryan on 2017/4/5.
 */
public class JavaMailWithAttachment {

        private MimeMessage message;
        private Session session;
        private Transport transport;

        private String mailHost = "";
        private String sender_username = "";
        private String sender_password = "";

        private Properties properties = new Properties();

        /*
         * 初始化方法
         */
        public JavaMailWithAttachment(boolean debug) {
            this.mailHost = ReadProperties.getValue("smtp");
            this.sender_username = ReadProperties.getValue("username");
            this.sender_password = ReadProperties.getValue("password");

            session = Session.getInstance(ReadProperties.pro);
//        session.setDebug(debug);// 开启后有调试信息
            message = new MimeMessage(session);
        }

        /**
         * 发送邮件
         *
         * @param subject     邮件主题
         * @param sendHtml    邮件内容
         * @param receiveUser 收件人地址
         * @param attachment  附件
         */
        public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, File attachment) {
            try {
                // 发件人 sender_username
                InternetAddress from = new InternetAddress(sender_username);
                message.setFrom(from);

                // 收件人
//            InternetAddress to = new InternetAddress(receiveUser);
                String[] receivers = receiveUser.split(";");
                String toList = getMailList(receivers);
                InternetAddress[] iaToList = new InternetAddress().parse(toList);
                message.setRecipients(Message.RecipientType.TO, iaToList);

                // 邮件主题
//            message.setSubject(subject);
                message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
                message.setSentDate(new Date());
                // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
                Multipart multipart = new MimeMultipart();

                // 添加邮件正文
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
                multipart.addBodyPart(contentPart);

                // 添加附件的内容
                if (attachment != null) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));

                    // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                    // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                    //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                    //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");

                    //MimeUtility.encodeWord可以避免文件名乱码
                    attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }

                // 将multipart对象放到message中
                message.setContent(multipart);
                // 保存邮件
                message.saveChanges();

                transport = session.getTransport("smtp");
                // smtp验证，就是你用来发邮件的邮箱用户名密码
                transport.connect(mailHost, sender_username, sender_password);
                // 发送
                transport.sendMessage(message, message.getAllRecipients());

                System.out.println("send success!");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (transport != null) {
                    try {
                        transport.close();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    private String getMailList(String[] mailArray){
        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if(mailArray!=null && length <2){
            toList.append(mailArray[0]);
        }else{
            for(int i=0;i<length;i++){
                toList.append(mailArray[i]);
                if(i!=(length-1)){
                    toList.append(",");
                }

            }
        }
        return toList.toString();
    }




}
