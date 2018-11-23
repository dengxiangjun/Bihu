package com.jd.dxj.util;
import com.jd.dxj.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description 邮箱工具
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/1 9:36
 **/
public class MailUtil {

    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    /**
     * 发送邮件线程池
     */
    private static ExecutorService executorService = Executors.newCachedThreadPool();

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
        logger.info("发送邮件,subject: " + subject + " ; text" + text);
        executorService.execute(()->{
            mailSender.send(simpleMailMessage);
        });
    }

}
