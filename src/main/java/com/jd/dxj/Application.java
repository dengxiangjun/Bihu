package com.jd.dxj;

import com.jd.dxj.model.Follow;
import com.jd.dxj.model.UserContent;
import com.jd.dxj.util.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description 主类
 *
 * @author dengxiangjun@jd.com
 * @date 2018/11/19 18:05
 **/
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.jd.dxj.mapper"})
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        init(args);
    }


    private static void init(String... args) throws Exception {
        ExecutorService voteAndComment = Executors.newCachedThreadPool();

        BihuCrack bihuCrack = SpringContext.getBean(BihuCrack.class);
        String accessToken = bihuCrack.login();
        if (accessToken != null) {

            //test(bihuCrack);//测试

            new Thread(() -> {
                while (true) {
                    List<Follow> follows = bihuCrack.getUserFollowList();
                    logger.info("关注人列表: " + follows);
                    for (Follow follow : follows) {
                        voteAndComment.execute(() -> {
                            if (follow.getFans() > 500){//大V
                                List<UserContent> userContents = bihuCrack.getUserContentList(follow);
                                for (UserContent userContent : userContents) {
                                    //logger.info(userContent.getContentId() + "");
                                    bihuCrack.upVote(userContent, bihuCrack.judgement());
                                    bihuCrack.createrootcomment(userContent, bihuCrack.judgement());//评论
                                }
                            }
                        });
                    }
                    logger.info("========================进入休眠");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else logger.error("登录错误");
    }

//    private static void test(BihuCrack bihuCrack) {
//        UserContent userContent = new UserContent();
//        userContent.setContentId(1948640403L);
//        bihuCrack.upVote(userContent, bihuCrack.judgement());
//        bihuCrack.createrootcomment(userContent, bihuCrack.judgement());//评论
//    }
}
