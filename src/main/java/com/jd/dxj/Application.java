package com.jd.dxj;

import com.jd.dxj.enums.BihuEnmus;
import com.jd.dxj.model.Follow;
import com.jd.dxj.model.UserContent;
import com.jd.dxj.util.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
        boot(args);
    }


    private static void boot(String... args) throws Exception {
        ExecutorService voteAndComment = Executors.newCachedThreadPool();

        BihuCrack bihuCrack = SpringContext.getBean(BihuCrack.class);
        String accessToken = bihuCrack.login();
        if (accessToken != null) {
            new Thread(() -> {
                while (true) {
                    List<Follow> follows = bihuCrack.getUserFollowList();
                    logger.info("关注人列表: " + follows);

                    List<Follow> famousFollows = follows.stream().filter((follow -> follow.getFans() >= BihuEnmus.FAMOUS_THRESHOLD)).collect(Collectors.toList());
                    //logger.info("大V个数：" + famousFollows.size());
                    for (Follow follow : famousFollows) {
                        voteAndComment.execute(() -> {
                            List<UserContent> userContents = bihuCrack.getUserContentList(follow);
                            for (UserContent userContent : userContents) {
                                //logger.info(userContent.getContentId() + "");
                                bihuCrack.upVote(userContent, bihuCrack.judgement());
                                bihuCrack.createrootcomment(userContent, bihuCrack.judgement());//评论
                            }
                        });
                    }
                    logger.info("========================进入休眠");
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //timerRereshFollow(bihuCrack);

        } else logger.error("登录错误");
    }

    private static void timerRereshFollow(BihuCrack bihuCrack) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                List<Follow> follows = bihuCrack.getUserFollowListByHttp();

            }
        };
        Timer timer = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每30分钟执行一次
        timer.schedule(timerTask, 10, 1000 * 1800);
    }
}
