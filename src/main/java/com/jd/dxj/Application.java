package com.jd.dxj;

import com.jd.dxj.enums.BihuEnmus;
import com.jd.dxj.model.Follow;
import com.jd.dxj.model.UserContent;
import com.jd.dxj.util.SpringContext;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
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
        ExecutorService voteAndComment = Executors.newFixedThreadPool(20);

        BihuCrack bihuCrack = SpringContext.getBean(BihuCrack.class);
        String accessToken = bihuCrack.login();
        if (accessToken != null) {
            List<Follow> follows = bihuCrack.getUserFollowList();
            List<Follow> famousFollows = follows.stream().filter((follow -> follow.getFans() >= BihuEnmus.FAMOUS_THRESHOLD)).collect(Collectors.toList());
            int famousCnt = famousFollows.size();
            logger.info("大V个数：" + famousCnt);
            while (true) {
                //logger.info("关注人列表: " + follows);
                CountDownLatch countDownLatch = new CountDownLatch(famousCnt);
                for (Follow follow : famousFollows) {
                    voteAndComment.execute(() -> {
                        logger.info("查找follow: " + follow);
                        List<UserContent> userContents = bihuCrack.getUserContentList(follow);
                        for (UserContent userContent : userContents) {
                            logger.info("准备点赞文章：" + bihuCrack.concatArticleLink(userContent));
                            bihuCrack.upVote(userContent, bihuCrack.judgement(userContent));
                            bihuCrack.createrootcomment(userContent, bihuCrack.judgement(userContent));//评论
                        }
                        countDownLatch.countDown();
                    });
                }

                countDownLatch.await();
                //voteAndComment.awaitTermination(3, TimeUnit.MINUTES);
                logger.info("========================进入休眠==============================================");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
