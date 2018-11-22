package com.jd.dxj.util;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * description 邮箱工具
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/1 9:36
 **/
public class MailUtil {

    /**
     * 发送文本邮件
     * @param mailSender JavaMailSender对象
     * @param sender 发送人
     * @param to 接收人
     * @param subject 主题
     * @param text 内容
     */
    public static void sendTxtMail(JavaMailSender mailSender, String sender, String[] to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        // 发送邮件
        mailSender.send(simpleMailMessage);
    }

}
